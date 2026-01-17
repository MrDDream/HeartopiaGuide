package com.heartopia.timer.data.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TimerDao {
    @Query("SELECT * FROM active_timers WHERE isCompleted = 0")
    fun getActiveTimers(): Flow<List<TimerEntity>>

    @Query("SELECT * FROM active_timers WHERE id = :id")
    suspend fun getTimerById(id: Long): TimerEntity?

    @Insert
    suspend fun insertTimer(timer: TimerEntity): Long

    @Update
    suspend fun updateTimer(timer: TimerEntity)

    @Query("UPDATE active_timers SET isCompleted = 1 WHERE id = :id")
    suspend fun markAsCompleted(id: Long)

    @Query("DELETE FROM active_timers WHERE id = :id")
    suspend fun deleteTimer(id: Long)

    @Query("DELETE FROM active_timers WHERE isCompleted = 1")
    suspend fun deleteCompletedTimers()
}
