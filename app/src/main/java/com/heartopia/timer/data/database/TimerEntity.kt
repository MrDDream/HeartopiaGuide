package com.heartopia.timer.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "active_timers")
data class TimerEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val cropName: String,
    val endTime: Long, // Timestamp en millisecondes
    val isCompleted: Boolean = false,
    val workRequestId: String? = null // ID du WorkRequest pour pouvoir l'annuler si nécessaire
)
