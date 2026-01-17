package com.heartopia.timer.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(CropViewModel::class.java) -> {
                CropViewModel(application) as T
            }
            modelClass.isAssignableFrom(SettingsViewModel::class.java) -> {
                SettingsViewModel(application) as T
            }
            modelClass.isAssignableFrom(RecipeViewModel::class.java) -> {
                RecipeViewModel(application) as T
            }
            modelClass.isAssignableFrom(BugViewModel::class.java) -> {
                BugViewModel(application) as T
            }
            modelClass.isAssignableFrom(FishViewModel::class.java) -> {
                FishViewModel(application) as T
            }
            modelClass.isAssignableFrom(BirdViewModel::class.java) -> {
                BirdViewModel(application) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
