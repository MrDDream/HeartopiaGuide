package com.heartopia.timer.data

import android.content.Context
import android.content.SharedPreferences

class SettingsManager(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("settings_prefs", Context.MODE_PRIVATE)

    enum class Language {
        FRENCH, ENGLISH
    }

    enum class ThemeMode {
        LIGHT, DARK
    }

    enum class NotificationType {
        VIBRATION, SOUND
    }

    companion object {
        private const val KEY_LANGUAGE = "language"
        private const val KEY_THEME_MODE = "theme_mode"
        private const val KEY_NOTIFICATIONS_ENABLED = "notifications_enabled"
        private const val KEY_NOTIFICATION_TYPE = "notification_type"
        
        private const val DEFAULT_LANGUAGE = "FRENCH"
        private const val DEFAULT_THEME_MODE = "DARK"
        private const val DEFAULT_NOTIFICATIONS_ENABLED = true
        private const val DEFAULT_NOTIFICATION_TYPE = "VIBRATION"
    }

    fun getLanguage(): Language {
        val languageName = sharedPreferences.getString(KEY_LANGUAGE, DEFAULT_LANGUAGE) ?: DEFAULT_LANGUAGE
        return try {
            Language.valueOf(languageName)
        } catch (e: IllegalArgumentException) {
            Language.FRENCH
        }
    }

    fun setLanguage(language: Language) {
        sharedPreferences.edit()
            .putString(KEY_LANGUAGE, language.name)
            .apply()
    }

    fun getThemeMode(): ThemeMode {
        val themeName = sharedPreferences.getString(KEY_THEME_MODE, DEFAULT_THEME_MODE) ?: DEFAULT_THEME_MODE
        return try {
            ThemeMode.valueOf(themeName)
        } catch (e: IllegalArgumentException) {
            ThemeMode.DARK
        }
    }

    fun setThemeMode(mode: ThemeMode) {
        sharedPreferences.edit()
            .putString(KEY_THEME_MODE, mode.name)
            .apply()
    }

    fun areNotificationsEnabled(): Boolean {
        return sharedPreferences.getBoolean(KEY_NOTIFICATIONS_ENABLED, DEFAULT_NOTIFICATIONS_ENABLED)
    }

    fun setNotificationsEnabled(enabled: Boolean) {
        sharedPreferences.edit()
            .putBoolean(KEY_NOTIFICATIONS_ENABLED, enabled)
            .apply()
    }

        fun getNotificationType(): NotificationType {
            val typeName = sharedPreferences.getString(KEY_NOTIFICATION_TYPE, DEFAULT_NOTIFICATION_TYPE) ?: DEFAULT_NOTIFICATION_TYPE
            return try {
                NotificationType.valueOf(typeName)
            } catch (e: IllegalArgumentException) {
                NotificationType.VIBRATION
            }
        }

    fun setNotificationType(type: NotificationType) {
        sharedPreferences.edit()
            .putString(KEY_NOTIFICATION_TYPE, type.name)
            .apply()
    }
}
