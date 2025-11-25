package com.example.habittracker.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "habits")
data class Habit(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val type: HabitType = HabitType.BINARY,
    val targetValue: Int? = null,
    val category: String? = null,
    val isActive: Boolean = true
)

enum class HabitType {
    BINARY,
    QUANTITATIVE
}
