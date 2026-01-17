package com.heartopia.timer.data

import android.content.Context
import com.heartopia.timer.R

data class Crop(
    val nameKey: String, // Clé de ressource string au lieu du nom direct
    val growthTimeMinutes: Int,
    val seedPrice: Int,
    val sellPrice: Int,
    val unlockLevel: Int
) {
    fun getName(context: Context): String {
        return when (nameKey) {
            "Tomate" -> context.getString(R.string.crop_tomato)
            "Pomme de terre" -> context.getString(R.string.crop_potato)
            "Blé" -> context.getString(R.string.crop_wheat)
            "Laitue" -> context.getString(R.string.crop_lettuce)
            "Ananas" -> context.getString(R.string.crop_pineapple)
            "Carotte" -> context.getString(R.string.crop_carrot)
            "Fraise" -> context.getString(R.string.crop_strawberry)
            else -> nameKey
        }
    }
    
    val name: String
        get() = nameKey // Pour compatibilité avec le code existant
    val growthTimeMillis: Long
        get() = growthTimeMinutes * 60 * 1000L

    fun getFormattedTime(): String {
        val hours = growthTimeMinutes / 60
        val minutes = growthTimeMinutes % 60
        
        return when {
            hours > 0 && minutes > 0 -> "${hours}h ${minutes}min"
            hours > 0 -> "${hours}h"
            else -> "${minutes}min"
        }
    }

    fun getIconResId(): Int {
        return when (nameKey) {
            "Tomate" -> com.heartopia.timer.R.drawable.ic_tomato
            "Pomme de terre" -> com.heartopia.timer.R.drawable.ic_potato
            "Blé" -> com.heartopia.timer.R.drawable.ic_wheat
            "Laitue" -> com.heartopia.timer.R.drawable.ic_lettuce
            "Ananas" -> com.heartopia.timer.R.drawable.ic_pineapple
            "Carotte" -> com.heartopia.timer.R.drawable.ic_carrot
            "Fraise" -> com.heartopia.timer.R.drawable.ic_strawberry
            else -> android.R.drawable.ic_menu_info_details
        }
    }

    companion object {
        fun getAllCrops(): List<Crop> {
            return listOf(
                Crop("Tomate", 15, 10, 30, 1),
                Crop("Pomme de terre", 60, 30, 90, 1),
                Crop("Blé", 240, 95, 285, 2), // 4 heures = 240 minutes
                Crop("Laitue", 480, 145, 435, 3), // 8 heures = 480 minutes
                Crop("Ananas", 30, 15, 52, 4),
                Crop("Carotte", 120, 50, 155, 5), // 2 heures = 120 minutes
                Crop("Fraise", 360, 125, 375, 6) // 6 heures = 360 minutes
            )
        }
        
        fun getCropDisplayName(context: Context, cropNameKey: String): String {
            return when (cropNameKey) {
                "Tomate" -> context.getString(R.string.crop_tomato)
                "Pomme de terre" -> context.getString(R.string.crop_potato)
                "Blé" -> context.getString(R.string.crop_wheat)
                "Laitue" -> context.getString(R.string.crop_lettuce)
                "Ananas" -> context.getString(R.string.crop_pineapple)
                "Carotte" -> context.getString(R.string.crop_carrot)
                "Fraise" -> context.getString(R.string.crop_strawberry)
                else -> cropNameKey
            }
        }
    }
}
