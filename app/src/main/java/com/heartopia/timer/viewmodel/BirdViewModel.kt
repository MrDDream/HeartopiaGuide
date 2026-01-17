package com.heartopia.timer.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.heartopia.timer.data.Bird
import com.heartopia.timer.data.BirdData

enum class FilterType {
    NAME,
    LEVEL,
    PRICE
}

enum class SortOrder {
    ASCENDING,
    DESCENDING
}

class BirdViewModel(application: Application) : AndroidViewModel(application) {
    
    val birds: List<Bird> = BirdData.getAllBirds()
    
    private val _sortedBirds = MutableLiveData<List<Bird>>()
    val sortedBirds: LiveData<List<Bird>> = _sortedBirds
    
    private val _searchQuery = MutableLiveData<String>("")
    val searchQuery: LiveData<String> = _searchQuery
    
    private val _filterType = MutableLiveData<FilterType>(FilterType.NAME)
    val filterType: LiveData<FilterType> = _filterType
    
    private val _sortOrder = MutableLiveData<SortOrder>(SortOrder.ASCENDING)
    val sortOrder: LiveData<SortOrder> = _sortOrder
    
    init {
        _sortedBirds.value = getFilteredAndSortedBirds("", FilterType.NAME, SortOrder.ASCENDING)
    }
    
    fun updateSortedBirds() {
        _sortedBirds.value = getFilteredAndSortedBirds(
            _searchQuery.value ?: "", 
            _filterType.value ?: FilterType.NAME,
            _sortOrder.value ?: SortOrder.ASCENDING
        )
    }
    
    fun setSearchQuery(query: String) {
        _searchQuery.value = query
        updateSortedBirds()
    }
    
    fun setFilterType(filterType: FilterType) {
        _filterType.value = filterType
        updateSortedBirds()
    }
    
    fun setSortOrder(sortOrder: SortOrder) {
        _sortOrder.value = sortOrder
        updateSortedBirds()
    }
    
    private fun getFilteredAndSortedBirds(searchQuery: String, filterType: FilterType, sortOrder: SortOrder): List<Bird> {
        Log.d("BirdViewModel", "getFilteredAndSortedBirds: query='$searchQuery', filterType=$filterType, sortOrder=$sortOrder")
        
        // Filtrer les oiseaux selon la recherche et le type de filtre
        val filteredBirds = if (searchQuery.isBlank()) {
            Log.d("BirdViewModel", "Search query is blank, returning all ${birds.size} birds")
            birds
        } else {
            val filtered = birds.filter { bird ->
                when (filterType) {
                    FilterType.NAME -> {
                        val birdName = getApplication<Application>().getString(bird.nameResId)
                        val matches = birdName.contains(searchQuery, ignoreCase = true)
                        if (matches) {
                            Log.d("BirdViewModel", "Match found: $birdName")
                        }
                        matches
                    }
                    FilterType.LEVEL -> {
                        val level = getApplication<Application>().getString(bird.levelResId)
                        level.contains(searchQuery, ignoreCase = true)
                    }
                    FilterType.PRICE -> {
                        val price = getApplication<Application>().getString(bird.priceResId)
                        price.contains(searchQuery, ignoreCase = true)
                    }
                }
            }
            Log.d("BirdViewModel", "Filtered ${filtered.size} birds from ${birds.size} total")
            filtered
        }
        
        // Trier selon le type de filtre et l'ordre
        return when (filterType) {
            FilterType.NAME -> {
                if (sortOrder == SortOrder.ASCENDING) {
                    filteredBirds.sortedBy { getApplication<Application>().getString(it.nameResId) }
                } else {
                    filteredBirds.sortedByDescending { getApplication<Application>().getString(it.nameResId) }
                }
            }
            FilterType.LEVEL -> {
                val sorted = filteredBirds.sortedWith(compareBy { bird ->
                    val levelStr = getApplication<Application>().getString(bird.levelResId)
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
                val sorted = filteredBirds.sortedWith(compareBy { bird ->
                    val priceStr = getApplication<Application>().getString(bird.priceResId)
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
