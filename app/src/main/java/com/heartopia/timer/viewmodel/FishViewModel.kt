package com.heartopia.timer.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.heartopia.timer.data.Fish
import com.heartopia.timer.data.FishData

class FishViewModel(application: Application) : AndroidViewModel(application) {
    
    val fish: List<Fish> = FishData.getAllFish()
    
    private val _sortedFish = MutableLiveData<List<Fish>>()
    val sortedFish: LiveData<List<Fish>> = _sortedFish
    
    private val _searchQuery = MutableLiveData<String>("")
    val searchQuery: LiveData<String> = _searchQuery
    
    private val _filterType = MutableLiveData<FilterType>(FilterType.NAME)
    val filterType: LiveData<FilterType> = _filterType
    
    private val _sortOrder = MutableLiveData<SortOrder>(SortOrder.ASCENDING)
    val sortOrder: LiveData<SortOrder> = _sortOrder
    
    init {
        _sortedFish.value = getFilteredAndSortedFish("", FilterType.NAME, SortOrder.ASCENDING)
    }
    
    fun updateSortedFish() {
        _sortedFish.value = getFilteredAndSortedFish(
            _searchQuery.value ?: "",
            _filterType.value ?: FilterType.NAME,
            _sortOrder.value ?: SortOrder.ASCENDING
        )
    }
    
    fun setSearchQuery(query: String) {
        _searchQuery.value = query
        updateSortedFish()
    }
    
    fun setFilterType(filterType: FilterType) {
        _filterType.value = filterType
        updateSortedFish()
    }
    
    fun setSortOrder(sortOrder: SortOrder) {
        _sortOrder.value = sortOrder
        updateSortedFish()
    }
    
    private fun getFilteredAndSortedFish(searchQuery: String, filterType: FilterType, sortOrder: SortOrder): List<Fish> {
        Log.d("FishViewModel", "getFilteredAndSortedFish: query='$searchQuery', filterType=$filterType, sortOrder=$sortOrder")
        
        // Filtrer les poissons selon la recherche et le type de filtre
        val filteredFish = if (searchQuery.isBlank()) {
            Log.d("FishViewModel", "Search query is blank, returning all ${fish.size} fish")
            fish
        } else {
            val filtered = fish.filter { fish ->
                when (filterType) {
                    FilterType.NAME -> {
                        val fishName = getApplication<Application>().getString(fish.nameResId)
                        val matches = fishName.contains(searchQuery, ignoreCase = true)
                        if (matches) {
                            Log.d("FishViewModel", "Match found: $fishName")
                        }
                        matches
                    }
                    FilterType.LEVEL -> {
                        val level = getApplication<Application>().getString(fish.levelResId)
                        level.contains(searchQuery, ignoreCase = true)
                    }
                    FilterType.PRICE -> {
                        val price = getApplication<Application>().getString(fish.priceResId)
                        price.contains(searchQuery, ignoreCase = true)
                    }
                }
            }
            Log.d("FishViewModel", "Filtered ${filtered.size} fish from ${fish.size} total")
            filtered
        }
        
        // Trier selon le type de filtre et l'ordre
        return when (filterType) {
            FilterType.NAME -> {
                if (sortOrder == SortOrder.ASCENDING) {
                    filteredFish.sortedBy { getApplication<Application>().getString(it.nameResId) }
                } else {
                    filteredFish.sortedByDescending { getApplication<Application>().getString(it.nameResId) }
                }
            }
            FilterType.LEVEL -> {
                val sorted = filteredFish.sortedWith(compareBy { fish ->
                    val levelStr = getApplication<Application>().getString(fish.levelResId)
                    // Extraire le numéro du niveau (ex: "Lv. 5" -> 5, "-" -> Int.MAX_VALUE)
                    try {
                        levelStr.replace("Lv.", "").replace("Lv", "").trim().toIntOrNull() ?: Int.MAX_VALUE
                    } catch (e: Exception) {
                        Int.MAX_VALUE
                    }
                })
                if (sortOrder == SortOrder.ASCENDING) sorted else sorted.reversed()
            }
            FilterType.PRICE -> {
                val sorted = filteredFish.sortedWith(compareBy { fish ->
                    val priceStr = getApplication<Application>().getString(fish.priceResId)
                    // Extraire le numéro du prix (ex: "27G" -> 27, "-" -> Int.MAX_VALUE)
                    try {
                        priceStr.replace("G", "").trim().toIntOrNull() ?: Int.MAX_VALUE
                    } catch (e: Exception) {
                        Int.MAX_VALUE
                    }
                })
                if (sortOrder == SortOrder.ASCENDING) sorted else sorted.reversed()
            }
        }
    }
}
