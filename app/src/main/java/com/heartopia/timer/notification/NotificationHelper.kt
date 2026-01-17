package com.heartopia.timer.notification

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.heartopia.timer.R
import com.heartopia.timer.ui.MainActivity

object NotificationHelper {
    private const val CHANNEL_ID_VIBRATION = "heartopia_timers_channel_vibration"
    private const val CHANNEL_ID_SOUND = "heartopia_timers_channel_sound"
    private const val CHANNEL_NAME = "Timers Heartopia"
    private const val NOTIFICATION_ID_BASE = 1000

    fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            
            // Canal pour les notifications avec vibration uniquement
            val vibrationChannel = NotificationChannel(
                CHANNEL_ID_VIBRATION,
                "$CHANNEL_NAME - Vibration",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = context.getString(R.string.notification_channel_description)
                enableVibration(true)
                enableLights(false)
                setSound(null, null) // Pas de son
                setShowBadge(false)
            }
            
            // Canal pour les notifications avec son uniquement
            val soundChannel = NotificationChannel(
                CHANNEL_ID_SOUND,
                "$CHANNEL_NAME - Son",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = context.getString(R.string.notification_channel_description)
                enableVibration(false) // Pas de vibration
                enableLights(false)
                setSound(android.provider.Settings.System.DEFAULT_NOTIFICATION_URI, null) // Son par défaut
                setShowBadge(false)
            }
            
            notificationManager.createNotificationChannel(vibrationChannel)
            notificationManager.createNotificationChannel(soundChannel)
        }
    }

    fun showNotification(context: Context, cropNameKey: String, timerId: Long) {
        // Vérifier la permission de notification pour Android 13+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // Permission non accordée, ne pas afficher la notification
                return
            }
        }

        // Vérifier si les notifications sont activées
        val settingsManager = com.heartopia.timer.data.SettingsManager(context)
        if (!settingsManager.areNotificationsEnabled()) {
            return
        }

        val notificationType = settingsManager.getNotificationType()

        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            timerId.toInt(),
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val cropDisplayName = com.heartopia.timer.data.Crop.getCropDisplayName(context, cropNameKey)
        
        // Choisir le canal approprié selon le type de notification
        val channelId = when (notificationType) {
            com.heartopia.timer.data.SettingsManager.NotificationType.VIBRATION -> CHANNEL_ID_VIBRATION
            com.heartopia.timer.data.SettingsManager.NotificationType.SOUND -> CHANNEL_ID_SOUND
        }
        
        val notificationBuilder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(context.getString(R.string.crop_ready, cropDisplayName))
            .setContentText(context.getString(R.string.crop_ready_message, cropDisplayName))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setDefaults(0) // Ne pas utiliser les valeurs par défaut
            .setLights(0, 0, 0) // Pas de lumière

        // Configurer le son et la vibration selon le type
        when (notificationType) {
            com.heartopia.timer.data.SettingsManager.NotificationType.VIBRATION -> {
                // Mode vibration uniquement : le canal gère déjà la vibration, pas de son
                notificationBuilder.setVibrate(longArrayOf(0, 500, 200, 500))
                notificationBuilder.setSound(null) // Désactiver explicitement le son
                vibrate(context) // Vibrer manuellement aussi
            }
            com.heartopia.timer.data.SettingsManager.NotificationType.SOUND -> {
                // Mode sonore uniquement : le canal gère déjà le son, pas de vibration
                notificationBuilder.setSound(android.provider.Settings.System.DEFAULT_NOTIFICATION_URI)
                notificationBuilder.setVibrate(null) // Désactiver explicitement la vibration
            }
        }

        val notification = notificationBuilder.build()

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify((NOTIFICATION_ID_BASE + timerId).toInt(), notification)
    }

    private fun vibrate(context: Context) {
        val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibratorManager.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(
                VibrationEffect.createWaveform(
                    longArrayOf(0, 500, 200, 500),
                    -1
                )
            )
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(longArrayOf(0, 500, 200, 500), -1)
        }
    }
}
