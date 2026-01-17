package com.heartopia.timer.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.heartopia.timer.data.database.AppDatabase
import com.heartopia.timer.notification.NotificationHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TimerNotificationWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            val timerId = inputData.getLong("timer_id", -1)
            val cropName = inputData.getString("crop_name") ?: return@withContext Result.failure()

            if (timerId == -1L) {
                return@withContext Result.failure()
            }

            // Marquer le timer comme complété dans la base de données
            val database = AppDatabase.getDatabase(applicationContext)
            database.timerDao().markAsCompleted(timerId)

            // Afficher la notification
            NotificationHelper.showNotification(applicationContext, cropName, timerId)

            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}
