package com.heartopia.timer.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.heartopia.timer.data.FavoritesManager
import com.heartopia.timer.data.Recipe
import com.heartopia.timer.data.RecipeData

class RecipeViewModel(application: Application) : AndroidViewModel(application) {
    private val favoritesManager: FavoritesManager = FavoritesManager(application)
    
    val recipes: List<Recipe> = RecipeData.getAllRecipes()
    
    private val _sortedRecipes = MutableLiveData<List<Recipe>>()
    val sortedRecipes: LiveData<List<Recipe>> = _sortedRecipes
    
    private val _searchQuery = MutableLiveData<String>("")
    val searchQuery: LiveData<String> = _searchQuery
    
    private val _filterType = MutableLiveData<FilterType>(FilterType.NAME)
    val filterType: LiveData<FilterType> = _filterType
    
    private val _sortOrder = MutableLiveData<SortOrder>(SortOrder.ASCENDING)
    val sortOrder: LiveData<SortOrder> = _sortOrder
    
    init {
        _sortedRecipes.value = getFilteredAndSortedRecipes("", FilterType.NAME, SortOrder.ASCENDING)
    }
    
    fun isFavorite(recipeNameResId: Int): Boolean {
        val recipeKey = getRecipeKey(recipeNameResId)
        return favoritesManager.isFavorite(recipeKey)
    }
    
    fun toggleFavorite(recipe: Recipe) {
        val recipeKey = getRecipeKey(recipe.nameResId)
        val isFavorite = favoritesManager.isFavorite(recipeKey)
        favoritesManager.setFavorite(recipeKey, !isFavorite)
        updateSortedRecipes()
    }
    
    fun updateSortedRecipes() {
        _sortedRecipes.value = getFilteredAndSortedRecipes(
            _searchQuery.value ?: "",
            _filterType.value ?: FilterType.NAME,
            _sortOrder.value ?: SortOrder.ASCENDING
        )
    }
    
    fun setSearchQuery(query: String) {
        _searchQuery.value = query
        updateSortedRecipes()
    }
    
    fun setFilterType(filterType: FilterType) {
        _filterType.value = filterType
        updateSortedRecipes()
    }
    
    fun setSortOrder(sortOrder: SortOrder) {
        _sortOrder.value = sortOrder
        updateSortedRecipes()
    }
    
    private fun getFilteredAndSortedRecipes(searchQuery: String, filterType: FilterType, sortOrder: SortOrder): List<Recipe> {
        Log.d("RecipeViewModel", "getFilteredAndSortedRecipes: query='$searchQuery', filterType=$filterType, sortOrder=$sortOrder")
        
        val favorites = favoritesManager.getFavorites()
        
        // Filtrer les recettes selon la recherche et le type de filtre
        val filteredRecipes = if (searchQuery.isBlank()) {
            Log.d("RecipeViewModel", "Search query is blank, returning all ${recipes.size} recipes")
            recipes
        } else {
            val filtered = recipes.filter { recipe ->
                when (filterType) {
                    FilterType.NAME -> {
                        val recipeName = getApplication<Application>().getString(recipe.nameResId)
                        val matches = recipeName.contains(searchQuery, ignoreCase = true)
                        if (matches) {
                            Log.d("RecipeViewModel", "Match found: $recipeName")
                        }
                        matches
                    }
                    FilterType.PRICE -> {
                        val price = getApplication<Application>().getString(recipe.priceResId)
                        price.contains(searchQuery, ignoreCase = true)
                    }
                    FilterType.LEVEL -> {
                        // Pour les recettes, on utilise l'énergie au lieu du niveau
                        val energy = getApplication<Application>().getString(recipe.energyResId)
                        energy.contains(searchQuery, ignoreCase = true)
                    }
                }
            }
            Log.d("RecipeViewModel", "Filtered ${filtered.size} recipes from ${recipes.size} total")
            filtered
        }
        
        // Trier selon le type de filtre et l'ordre
        val sorted = when (filterType) {
            FilterType.NAME -> {
                if (sortOrder == SortOrder.ASCENDING) {
                    filteredRecipes.sortedBy { getApplication<Application>().getString(it.nameResId) }
                } else {
                    filteredRecipes.sortedByDescending { getApplication<Application>().getString(it.nameResId) }
                }
            }
            FilterType.PRICE -> {
                val sortedList = filteredRecipes.sortedWith(compareBy { recipe ->
                    val priceStr = getApplication<Application>().getString(recipe.priceResId)
                    // Extraire le numéro du prix (ex: "290G" -> 290, "-" -> Int.MAX_VALUE)
                    try {
                        val cleanPrice = priceStr.replace("G", "").trim()
                        cleanPrice.toIntOrNull() ?: Int.MAX_VALUE
                    } catch (e: Exception) {
                        Int.MAX_VALUE
                    }
                })
                if (sortOrder == SortOrder.ASCENDING) sortedList else sortedList.reversed()
            }
            FilterType.LEVEL -> {
                // Pour les recettes, on trie par énergie
                val sortedList = filteredRecipes.sortedWith(compareBy { recipe ->
                    val energyStr = getApplication<Application>().getString(recipe.energyResId)
                    // Extraire le numéro de l'énergie (ex: "50" -> 50, "-" -> Int.MAX_VALUE)
                    try {
                        energyStr.trim().toIntOrNull() ?: Int.MAX_VALUE
                    } catch (e: Exception) {
                        Int.MAX_VALUE
                    }
                })
                if (sortOrder == SortOrder.ASCENDING) sortedList else sortedList.reversed()
            }
        }
        
        // Trier par favoris en premier, puis selon le tri principal
        return sorted.sortedWith(compareBy<Recipe> { recipe ->
            val recipeKey = getRecipeKey(recipe.nameResId)
            when {
                favorites.contains(recipeKey) -> 0  // Favoris en premier
                else -> 1  // Le reste
            }
        }.thenComparator { recipe1, recipe2 ->
            // Comparer selon le type de filtre et l'ordre
            when (filterType) {
                FilterType.NAME -> {
                    val name1 = getApplication<Application>().getString(recipe1.nameResId)
                    val name2 = getApplication<Application>().getString(recipe2.nameResId)
                    if (sortOrder == SortOrder.ASCENDING) {
                        name1.compareTo(name2)
                    } else {
                        name2.compareTo(name1)
                    }
                }
                FilterType.PRICE -> {
                    val price1 = getApplication<Application>().getString(recipe1.priceResId)
                    val price2 = getApplication<Application>().getString(recipe2.priceResId)
                    val num1 = try {
                        price1.replace("G", "").trim().toIntOrNull() ?: Int.MAX_VALUE
                    } catch (e: Exception) {
                        Int.MAX_VALUE
                    }
                    val num2 = try {
                        price2.replace("G", "").trim().toIntOrNull() ?: Int.MAX_VALUE
                    } catch (e: Exception) {
                        Int.MAX_VALUE
                    }
                    if (sortOrder == SortOrder.ASCENDING) {
                        num1.compareTo(num2)
                    } else {
                        num2.compareTo(num1)
                    }
                }
                FilterType.LEVEL -> {
                    val energy1 = getApplication<Application>().getString(recipe1.energyResId)
                    val energy2 = getApplication<Application>().getString(recipe2.energyResId)
                    val num1 = try {
                        energy1.trim().toIntOrNull() ?: Int.MAX_VALUE
                    } catch (e: Exception) {
                        Int.MAX_VALUE
                    }
                    val num2 = try {
                        energy2.trim().toIntOrNull() ?: Int.MAX_VALUE
                    } catch (e: Exception) {
                        Int.MAX_VALUE
                    }
                    if (sortOrder == SortOrder.ASCENDING) {
                        num1.compareTo(num2)
                    } else {
                        num2.compareTo(num1)
                    }
                }
            }
        })
    }
    
    private fun getRecipeKey(nameResId: Int): String {
        return "recipe_${nameResId}"
    }
}
