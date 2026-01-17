package com.heartopia.timer.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.heartopia.timer.data.Bug
import com.heartopia.timer.data.BugData

class BugViewModel(application: Application) : AndroidViewModel(application) {
    
    val bugs: List<Bug> = BugData.getAllBugs()
    
    private val _sortedBugs = MutableLiveData<List<Bug>>()
    val sortedBugs: LiveData<List<Bug>> = _sortedBugs
    
    private val _searchQuery = MutableLiveData<String>("")
    val searchQuery: LiveData<String> = _searchQuery
    
    private val _filterType = MutableLiveData<FilterType>(FilterType.NAME)
    val filterType: LiveData<FilterType> = _filterType
    
    private val _sortOrder = MutableLiveData<SortOrder>(SortOrder.ASCENDING)
    val sortOrder: LiveData<SortOrder> = _sortOrder
    
    init {
        _sortedBugs.value = getFilteredAndSortedBugs("", FilterType.NAME, SortOrder.ASCENDING)
    }
    
    fun updateSortedBugs() {
        _sortedBugs.value = getFilteredAndSortedBugs(
            _searchQuery.value ?: "",
            _filterType.value ?: FilterType.NAME,
            _sortOrder.value ?: SortOrder.ASCENDING
        )
    }
    
    fun setSearchQuery(query: String) {
        _searchQuery.value = query
        updateSortedBugs()
    }
    
    fun setFilterType(filterType: FilterType) {
        _filterType.value = filterType
        updateSortedBugs()
    }
    
    fun setSortOrder(sortOrder: SortOrder) {
        _sortOrder.value = sortOrder
        updateSortedBugs()
    }
    
    private fun getFilteredAndSortedBugs(searchQuery: String, filterType: FilterType, sortOrder: SortOrder): List<Bug> {
        Log.d("BugViewModel", "getFilteredAndSortedBugs: query='$searchQuery', filterType=$filterType, sortOrder=$sortOrder")
        
        // Filtrer les insectes selon la recherche et le type de filtre
        val filteredBugs = if (searchQuery.isBlank()) {
            Log.d("BugViewModel", "Search query is blank, returning all ${bugs.size} bugs")
            bugs
        } else {
            val filtered = bugs.filter { bug ->
                when (filterType) {
                    FilterType.NAME -> {
                        val bugName = getApplication<Application>().getString(bug.nameResId)
                        val matches = bugName.contains(searchQuery, ignoreCase = true)
                        if (matches) {
                            Log.d("BugViewModel", "Match found: $bugName")
                        }
                        matches
                    }
                    FilterType.LEVEL -> {
                        val level = getApplication<Application>().getString(bug.levelResId)
                        level.contains(searchQuery, ignoreCase = true)
                    }
                    FilterType.PRICE -> {
                        val price = getApplication<Application>().getString(bug.priceResId)
                        price.contains(searchQuery, ignoreCase = true)
                    }
                }
            }
            Log.d("BugViewModel", "Filtered ${filtered.size} bugs from ${bugs.size} total")
            filtered
        }
        
        // Trier selon le type de filtre et l'ordre
        return when (filterType) {
            FilterType.NAME -> {
                if (sortOrder == SortOrder.ASCENDING) {
                    filteredBugs.sortedBy { getApplication<Application>().getString(it.nameResId) }
                } else {
                    filteredBugs.sortedByDescending { getApplication<Application>().getString(it.nameResId) }
                }
            }
            FilterType.LEVEL -> {
                val sorted = filteredBugs.sortedWith(compareBy { bug ->
                    val levelStr = getApplication<Application>().getString(bug.levelResId)
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
                val sorted = filteredBugs.sortedWith(compareBy { bug ->
                    val priceStr = getApplication<Application>().getString(bug.priceResId)
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
