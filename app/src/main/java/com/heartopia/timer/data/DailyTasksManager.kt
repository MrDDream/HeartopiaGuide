package com.heartopia.timer.data

import android.content.Context
import android.content.SharedPreferences
import java.util.Calendar
import java.util.TimeZone

class DailyTasksManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences(
        "heartopia_daily_tasks",
        Context.MODE_PRIVATE
    )
    
    private val KEY_LAST_RESET_DATE = "last_reset_date"
    private val TASK_PREFIX = "task_"
    private val CUSTOM_TASKS_KEY = "custom_tasks"
    
    companion object {
        val TASK_KEYS = listOf(
            "inhabitants_tasks",
            "furniture_shop",
            "clothing_shop",
            "feed_wild_animals",
            "collect_sliding_oak",
            "collect_fluorite",
            "check_event_tasks"
        )
    }
    
    private fun getLastResetDate(): Long {
        return prefs.getLong(KEY_LAST_RESET_DATE, 0)
    }
    
    private fun setLastResetDate(date: Long) {
        prefs.edit().putLong(KEY_LAST_RESET_DATE, date).apply()
    }
    
    private fun getCurrentDateInUTC1(): Long {
        val timeZone = TimeZone.getTimeZone("Europe/Paris")
        val calendar = Calendar.getInstance(timeZone)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.timeInMillis
    }
    
    fun checkAndResetIfNeeded() {
        val lastResetDate = getLastResetDate()
        val currentDate = getCurrentDateInUTC1()
        
        // Si la date de reset est différente ou si c'est la première fois
        if (lastResetDate != currentDate) {
            // Réinitialiser toutes les tâches prédéfinies
            TASK_KEYS.forEach { taskKey ->
                prefs.edit().putBoolean(TASK_PREFIX + taskKey, false).apply()
            }
            
            // Réinitialiser toutes les tâches personnalisées (mais ne pas les supprimer)
            getCustomTasks().forEach { taskKey ->
                prefs.edit().putBoolean(TASK_PREFIX + taskKey, false).apply()
            }
            
            setLastResetDate(currentDate)
        }
    }
    
    fun getCustomTasks(): Set<String> {
        return prefs.getStringSet(CUSTOM_TASKS_KEY, emptySet()) ?: emptySet()
    }
    
    fun addCustomTask(taskName: String): String {
        val taskKey = "custom_${System.currentTimeMillis()}"
        val customTasks = getCustomTasks().toMutableSet()
        customTasks.add(taskKey)
        prefs.edit()
            .putStringSet(CUSTOM_TASKS_KEY, customTasks)
            .putString("task_name_$taskKey", taskName)
            .putBoolean(TASK_PREFIX + taskKey, false)
            .apply()
        return taskKey
    }
    
    fun removeCustomTask(taskKey: String) {
        val customTasks = getCustomTasks().toMutableSet()
        customTasks.remove(taskKey)
        prefs.edit()
            .putStringSet(CUSTOM_TASKS_KEY, customTasks)
            .remove("task_name_$taskKey")
            .remove(TASK_PREFIX + taskKey)
            .apply()
    }
    
    fun getCustomTaskName(taskKey: String): String? {
        return prefs.getString("task_name_$taskKey", null)
    }
    
    fun isCustomTask(taskKey: String): Boolean {
        return taskKey.startsWith("custom_")
    }
    
    fun isTaskCompleted(taskKey: String): Boolean {
        checkAndResetIfNeeded()
        return prefs.getBoolean(TASK_PREFIX + taskKey, false)
    }
    
    fun setTaskCompleted(taskKey: String, completed: Boolean) {
        prefs.edit().putBoolean(TASK_PREFIX + taskKey, completed).apply()
    }
}
