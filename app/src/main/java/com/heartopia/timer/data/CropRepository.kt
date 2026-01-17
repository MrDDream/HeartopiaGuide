package com.heartopia.timer.data

import com.heartopia.timer.data.database.AppDatabase
import com.heartopia.timer.data.database.TimerEntity
import kotlinx.coroutines.flow.Flow

class CropRepository(private val database: AppDatabase) {
    fun getAllCrops(): List<Crop> = Crop.getAllCrops()

    fun getActiveTimers(): Flow<List<TimerEntity>> = database.timerDao().getActiveTimers()

    suspend fun insertTimer(timer: TimerEntity): Long = database.timerDao().insertTimer(timer)

    suspend fun updateTimer(timer: TimerEntity) = database.timerDao().updateTimer(timer)

    suspend fun getTimerById(id: Long): TimerEntity? = database.timerDao().getTimerById(id)

    suspend fun markTimerAsCompleted(id: Long) = database.timerDao().markAsCompleted(id)

    suspend fun deleteTimer(id: Long) = database.timerDao().deleteTimer(id)
}
