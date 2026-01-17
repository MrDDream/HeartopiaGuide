package com.heartopia.timer.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.heartopia.timer.data.SettingsManager

class SettingsViewModel(application: Application) : AndroidViewModel(application) {
    private val settingsManager: SettingsManager = SettingsManager(application)

    private val _language = MutableLiveData<SettingsManager.Language>()
    val language: LiveData<SettingsManager.Language> = _language

    private val _themeMode = MutableLiveData<SettingsManager.ThemeMode>()
    val themeMode: LiveData<SettingsManager.ThemeMode> = _themeMode

    private val _notificationsEnabled = MutableLiveData<Boolean>()
    val notificationsEnabled: LiveData<Boolean> = _notificationsEnabled

    private val _notificationType = MutableLiveData<SettingsManager.NotificationType>()
    val notificationType: LiveData<SettingsManager.NotificationType> = _notificationType

    init {
        loadSettings()
    }

    private fun loadSettings() {
        _language.value = settingsManager.getLanguage()
        _themeMode.value = settingsManager.getThemeMode()
        _notificationsEnabled.value = settingsManager.areNotificationsEnabled()
        _notificationType.value = settingsManager.getNotificationType()
    }

    fun setLanguage(language: SettingsManager.Language) {
        settingsManager.setLanguage(language)
        _language.value = language
    }

    fun setThemeMode(mode: SettingsManager.ThemeMode) {
        settingsManager.setThemeMode(mode)
        _themeMode.value = mode
    }

    fun setNotificationsEnabled(enabled: Boolean) {
        settingsManager.setNotificationsEnabled(enabled)
        _notificationsEnabled.value = enabled
    }

    fun setNotificationType(type: SettingsManager.NotificationType) {
        settingsManager.setNotificationType(type)
        _notificationType.value = type
    }
}
