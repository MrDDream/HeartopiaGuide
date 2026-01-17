package com.heartopia.timer.data

import android.content.Context
import android.content.SharedPreferences

class FavoritesManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences(
        "heartopia_favorites",
        Context.MODE_PRIVATE
    )

    fun isFavorite(cropName: String): Boolean {
        return prefs.getBoolean(cropName, false)
    }

    fun setFavorite(cropName: String, isFavorite: Boolean) {
        prefs.edit().putBoolean(cropName, isFavorite).apply()
    }

    fun getFavorites(): Set<String> {
        return prefs.all.keys.filter { prefs.getBoolean(it, false) }.toSet()
    }
}
