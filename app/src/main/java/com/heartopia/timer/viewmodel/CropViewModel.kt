package com.heartopia.timer.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.heartopia.timer.R
import com.heartopia.timer.data.Crop
import com.heartopia.timer.data.CropRepository
import com.heartopia.timer.data.FavoritesManager
import com.heartopia.timer.data.database.AppDatabase
import com.heartopia.timer.data.database.TimerEntity
import com.heartopia.timer.service.TimerForegroundService
import com.heartopia.timer.worker.TimerNotificationWorker
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.UUID

class CropViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: CropRepository
    private val workManager: WorkManager
    private val favoritesManager: FavoritesManager

    val crops: List<Crop> = Crop.getAllCrops()
    val activeTimers: LiveData<List<TimerEntity>>

    private val _sortedCrops = MutableLiveData<List<Crop>>()
    val sortedCrops: LiveData<List<Crop>> = _sortedCrops
    
    private val _searchQuery = MutableLiveData<String>("")
    val searchQuery: LiveData<String> = _searchQuery

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    private val _successMessage = MutableLiveData<String?>()
    val successMessage: LiveData<String?> = _successMessage

    private val _showStopTimerDialog = MutableLiveData<TimerEntity?>()
    val showStopTimerDialog: LiveData<TimerEntity?> = _showStopTimerDialog

    private val _showFertilizerDialog = MutableLiveData<Pair<Long, Int>?>()
    val showFertilizerDialog: LiveData<Pair<Long, Int>?> = _showFertilizerDialog

    init {
        val database = AppDatabase.getDatabase(application)
        repository = CropRepository(database)
        workManager = WorkManager.getInstance(application)
        favoritesManager = FavoritesManager(application)
        activeTimers = repository.getActiveTimers().asLiveData()
        
        // Initialiser la liste triée
        _sortedCrops.value = getFilteredAndSortedCrops(emptyList(), "")
    }
    
    fun restoreNotificationIfNeeded() {
        viewModelScope.launch {
            val settingsManager = com.heartopia.timer.data.SettingsManager(getApplication())
            if (!settingsManager.areNotificationsEnabled()) {
                return@launch
            }
            val timers = repository.getActiveTimers().first()
            val activeTimers = timers.filter { 
                !it.isCompleted && it.endTime > System.currentTimeMillis() 
            }
            if (activeTimers.isNotEmpty()) {
                TimerForegroundService.startService(getApplication())
            }
        }
    }

    fun isFavorite(cropName: String): Boolean {
        return favoritesManager.isFavorite(cropName)
    }

    fun toggleFavorite(crop: Crop) {
        val isFavorite = favoritesManager.isFavorite(crop.nameKey)
        favoritesManager.setFavorite(crop.nameKey, !isFavorite)
        updateSortedCrops()
    }

    fun updateSortedCrops() {
        _sortedCrops.value = getFilteredAndSortedCrops(activeTimers.value ?: emptyList(), _searchQuery.value ?: "")
    }
    
    fun setSearchQuery(query: String) {
        _searchQuery.value = query
        updateSortedCrops()
    }

    private fun getFilteredAndSortedCrops(activeTimers: List<TimerEntity>, searchQuery: String): List<Crop> {
        val favorites = favoritesManager.getFavorites()
        
        // Filtrer les cultures selon la recherche
        val filteredCrops = if (searchQuery.isBlank()) {
            crops
        } else {
            crops.filter { crop ->
                crop.getName(getApplication()).contains(searchQuery, ignoreCase = true) ||
                crop.nameKey.contains(searchQuery, ignoreCase = true)
            }
        }
        
        return filteredCrops.sortedWith(compareBy<Crop> { crop ->
            when {
                favorites.contains(crop.nameKey) -> 0  // Favoris en premier
                else -> 1  // Le reste
            }
        }.thenBy { it.unlockLevel }  // Trier par niveau croissant dans chaque groupe
        .thenBy { it.nameKey })  // Puis par nom en cas d'égalité de niveau
    }

    fun startTimer(crop: Crop) {
        viewModelScope.launch {
            try {
                val currentTime = System.currentTimeMillis()
                val endTime = currentTime + crop.growthTimeMillis

                // Créer l'entité timer (utiliser nameKey pour la compatibilité)
                val timerEntity = TimerEntity(
                    cropName = crop.nameKey,
                    endTime = endTime,
                    isCompleted = false
                )

                // Sauvegarder dans la base de données
                val timerId = repository.insertTimer(timerEntity)

                // Créer les données pour le Worker
                val inputData = Data.Builder()
                    .putLong("timer_id", timerId)
                    .putString("crop_name", crop.nameKey)
                    .build()

                // Programmer le WorkManager pour la notification
                val workRequest = OneTimeWorkRequestBuilder<TimerNotificationWorker>()
                    .setInitialDelay(crop.growthTimeMillis, java.util.concurrent.TimeUnit.MILLISECONDS)
                    .setInputData(inputData)
                    .addTag("timer_$timerId")
                    .build()

                workManager.enqueue(workRequest)

                // Mettre à jour l'entité avec l'ID du work request
                val updatedTimer = timerEntity.copy(id = timerId, workRequestId = workRequest.id.toString())
                repository.updateTimer(updatedTimer)

                // Démarrer le service foreground si nécessaire (seulement si les notifications sont activées)
                val settingsManager = com.heartopia.timer.data.SettingsManager(getApplication())
                if (settingsManager.areNotificationsEnabled()) {
                    TimerForegroundService.startService(getApplication())
                }

                val cropDisplayName = crop.getName(getApplication())
                _successMessage.value = getApplication<Application>().getString(R.string.timer_started, cropDisplayName)
            } catch (e: Exception) {
                _errorMessage.value = "Erreur lors du démarrage du timer: ${e.message}"
            }
        }
    }

    fun requestStopTimer(timerId: Long) {
        viewModelScope.launch {
            try {
                val timer = repository.getTimerById(timerId)
                if (timer != null) {
                    _showStopTimerDialog.value = timer
                }
            } catch (e: Exception) {
                _errorMessage.value = "Erreur lors de la récupération du timer: ${e.message}"
            }
        }
    }

    fun confirmStopTimer(timerId: Long) {
        viewModelScope.launch {
            try {
                val timer = repository.getTimerById(timerId)
                if (timer != null) {
                    // Annuler le WorkRequest si disponible
                    timer.workRequestId?.let { workRequestId ->
                        try {
                            val uuid = UUID.fromString(workRequestId)
                            workManager.cancelWorkById(uuid)
                        } catch (e: Exception) {
                            // Ignorer si le format n'est pas valide
                        }
                    }

                                // Supprimer le timer de la base de données
                                repository.deleteTimer(timerId)

                                val cropDisplayName = com.heartopia.timer.data.Crop.getCropDisplayName(getApplication(), timer.cropName)
                                _successMessage.value = getApplication<Application>().getString(R.string.timer_stopped, cropDisplayName)
                }
            } catch (e: Exception) {
                _errorMessage.value = "Erreur lors de l'arrêt du timer: ${e.message}"
            }
        }
    }

    fun clearStopTimerDialog() {
        _showStopTimerDialog.value = null
    }

    fun clearMessages() {
        _errorMessage.value = null
        _successMessage.value = null
    }

    fun requestApplyFertilizer(timerId: Long, amount: Int) {
        viewModelScope.launch {
            try {
                val timer = repository.getTimerById(timerId)
                if (timer != null && !timer.isCompleted) {
                    _showFertilizerDialog.value = Pair(timerId, amount)
                }
            } catch (e: Exception) {
                _errorMessage.value = "Erreur lors de la récupération du timer: ${e.message}"
            }
        }
    }

    fun confirmApplyFertilizer(timerId: Long, amount: Int) {
        viewModelScope.launch {
            try {
                val timer = repository.getTimerById(timerId)
                if (timer != null && !timer.isCompleted) {
                    // Réduire le timer de 15 minutes × le nombre d'engrais
                    val reductionMillis = amount * 15 * 60 * 1000L
                    val newEndTime = timer.endTime - reductionMillis
                    
                    // S'assurer que le nouveau temps n'est pas dans le passé
                    val currentTime = System.currentTimeMillis()
                    val finalEndTime = maxOf(newEndTime, currentTime + 1000) // Au moins 1 seconde restante
                    
                    // Mettre à jour le timer
                    val updatedTimer = timer.copy(endTime = finalEndTime)
                    repository.updateTimer(updatedTimer)
                    
                    // Annuler l'ancien WorkRequest et en créer un nouveau avec le nouveau temps
                    timer.workRequestId?.let { workRequestId ->
                        try {
                            val uuid = UUID.fromString(workRequestId)
                            workManager.cancelWorkById(uuid)
                        } catch (e: Exception) {
                            // Ignorer si le format n'est pas valide
                        }
                    }
                    
                    // Créer un nouveau WorkRequest avec le nouveau temps
                    val remainingTime = finalEndTime - currentTime
                    if (remainingTime > 0) {
                        val inputData = Data.Builder()
                            .putLong("timer_id", timerId)
                            .putString("crop_name", timer.cropName)
                            .build()

                        val workRequest = OneTimeWorkRequestBuilder<TimerNotificationWorker>()
                            .setInitialDelay(remainingTime, java.util.concurrent.TimeUnit.MILLISECONDS)
                            .setInputData(inputData)
                            .addTag("timer_$timerId")
                            .build()

                        workManager.enqueue(workRequest)

                        // Mettre à jour l'entité avec le nouvel ID du work request
                        val finalTimer = updatedTimer.copy(workRequestId = workRequest.id.toString())
                        repository.updateTimer(finalTimer)
                    }
                    
                    val reductionMinutes = amount * 15
                    _successMessage.value = getApplication<Application>().getString(
                        R.string.fertilizer_success,
                        reductionMinutes
                    )
                }
            } catch (e: Exception) {
                _errorMessage.value = "Erreur lors de l'application de l'engrais: ${e.message}"
            }
        }
    }

    fun clearFertilizerDialog() {
        _showFertilizerDialog.value = null
    }
}
