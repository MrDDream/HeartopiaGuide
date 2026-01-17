package com.heartopia.timer

import android.app.Application
import com.heartopia.timer.notification.NotificationHelper

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        NotificationHelper.createNotificationChannel(this)
    }
}
