package com.heartopia.timer.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.app.ServiceCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import com.heartopia.timer.R
import com.heartopia.timer.data.database.AppDatabase
import com.heartopia.timer.data.database.TimerEntity
import com.heartopia.timer.ui.MainActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class TimerForegroundService : LifecycleService() {

    companion object {
        private const val CHANNEL_ID = "timer_foreground_channel"
        private const val NOTIFICATION_ID = 1
        private const val ACTION_STOP_SERVICE = "com.heartopia.timer.STOP_SERVICE"

        fun startService(context: Context) {
            // Vérifier si les notifications sont activées avant de démarrer le service
            val settingsManager = com.heartopia.timer.data.SettingsManager(context)
            if (!settingsManager.areNotificationsEnabled()) {
                return
            }
            val intent = Intent(context, TimerForegroundService::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(intent)
            } else {
                context.startService(intent)
            }
        }

        fun stopService(context: Context) {
            val intent = Intent(context, TimerForegroundService::class.java)
            context.stopService(intent)
        }
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)

        // Vérifier si les notifications sont activées
        val settingsManager = com.heartopia.timer.data.SettingsManager(this)
        if (!settingsManager.areNotificationsEnabled()) {
            stopForeground(Service.STOP_FOREGROUND_REMOVE)
            stopSelf()
            return START_NOT_STICKY
        }

        if (intent?.action == ACTION_STOP_SERVICE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                stopForeground(Service.STOP_FOREGROUND_REMOVE)
            } else {
                @Suppress("DEPRECATION")
                stopForeground(true)
            }
            stopSelf()
            return START_NOT_STICKY
        }

        val notification = createNotification(emptyList())
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            // Android 14+ nécessite le type de service spécial
            ServiceCompat.startForeground(
                this,
                NOTIFICATION_ID,
                notification,
                android.content.pm.ServiceInfo.FOREGROUND_SERVICE_TYPE_SPECIAL_USE
            )
        } else {
            @Suppress("DEPRECATION")
            startForeground(NOTIFICATION_ID, notification)
        }

        lifecycleScope.launch {
            val database = AppDatabase.getDatabase(applicationContext)
            var updateJob: kotlinx.coroutines.Job? = null
            
            database.timerDao().getActiveTimers().collect { timers ->
                val activeTimers = timers.filter { 
                    !it.isCompleted && it.endTime > System.currentTimeMillis() 
                }
                
                // Arrêter la mise à jour précédente
                updateJob?.cancel()
                
                if (activeTimers.isEmpty()) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        stopForeground(Service.STOP_FOREGROUND_REMOVE)
                    } else {
                        @Suppress("DEPRECATION")
                        stopForeground(true)
                    }
                    stopSelf()
                } else {
                    // Mettre à jour la notification toutes les secondes pour afficher les secondes
                    updateJob = updateNotificationPeriodically(activeTimers)
                }
            }
        }

        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        super.onBind(intent)
        return null
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                getString(R.string.notification_channel_name),
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = getString(R.string.notification_channel_description)
                setShowBadge(false)
            }

            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun createNotification(timers: List<TimerEntity>): Notification {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(getString(R.string.active_timers))
            .setSmallIcon(android.R.drawable.ic_menu_recent_history)
            .setContentIntent(pendingIntent)
            .setOngoing(true)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setCategory(NotificationCompat.CATEGORY_SERVICE)

        if (timers.isEmpty()) {
            notificationBuilder.setContentText(getString(R.string.no_active_timers))
        } else {
            // Utiliser InboxStyle pour afficher plusieurs lignes
            val style = NotificationCompat.InboxStyle()
                .setBigContentTitle(getString(R.string.active_timers))
            
            timers.forEach { timer ->
                val remainingTime = timer.endTime - System.currentTimeMillis()
                val hours = TimeUnit.MILLISECONDS.toHours(remainingTime)
                val minutes = TimeUnit.MILLISECONDS.toMinutes(remainingTime) % 60
                val seconds = TimeUnit.MILLISECONDS.toSeconds(remainingTime) % 60
                
                val timeText = when {
                    hours > 0 -> String.format("%d:%02d:%02d", hours, minutes, seconds)
                    else -> String.format("%02d:%02d", minutes, seconds)
                }
                
                val cropDisplayName = com.heartopia.timer.data.Crop.getCropDisplayName(this, timer.cropName)
                style.addLine("$cropDisplayName: $timeText")
            }
            
            if (timers.size > 7) {
                style.setSummaryText("+${timers.size - 7} autres")
            }
            
            notificationBuilder.setStyle(style)
            
            // Texte court pour la notification compacte
            val firstTimer = timers.first()
            val remainingTime = firstTimer.endTime - System.currentTimeMillis()
            val hours = TimeUnit.MILLISECONDS.toHours(remainingTime)
            val minutes = TimeUnit.MILLISECONDS.toMinutes(remainingTime) % 60
            val seconds = TimeUnit.MILLISECONDS.toSeconds(remainingTime) % 60
            
            val timeText = when {
                hours > 0 -> String.format("%d:%02d:%02d", hours, minutes, seconds)
                else -> String.format("%02d:%02d", minutes, seconds)
            }
            
            val firstCropDisplayName = com.heartopia.timer.data.Crop.getCropDisplayName(this, firstTimer.cropName)
            notificationBuilder.setContentText("$firstCropDisplayName: $timeText${if (timers.size > 1) " (+${timers.size - 1})" else ""}")
        }

        return notificationBuilder.build()
    }

    private fun updateNotificationPeriodically(activeTimers: List<TimerEntity>): kotlinx.coroutines.Job {
        return lifecycleScope.launch {
            while (true) {
                // Vérifier si les notifications sont toujours activées
                val settingsManager = com.heartopia.timer.data.SettingsManager(this@TimerForegroundService)
                if (!settingsManager.areNotificationsEnabled()) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        stopForeground(Service.STOP_FOREGROUND_REMOVE)
                    } else {
                        @Suppress("DEPRECATION")
                        stopForeground(true)
                    }
                    stopSelf()
                    break
                }
                
                val currentTimers = activeTimers.filter { 
                    !it.isCompleted && it.endTime > System.currentTimeMillis() 
                }
                
                if (currentTimers.isEmpty()) {
                    break
                }
                
                val notification = createNotification(currentTimers)
                val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.notify(NOTIFICATION_ID, notification)
                
                delay(1000) // Mettre à jour toutes les secondes
            }
        }
    }
}
