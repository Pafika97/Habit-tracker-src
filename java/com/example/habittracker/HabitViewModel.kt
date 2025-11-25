package com.example.habittracker

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.habittracker.data.*
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class HabitWithTodayProgress(
    val habit: Habit,
    val todayCount: Int
)

class HabitViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = AppDatabase.getInstance(application).habitDao()

    val habitsWithToday: StateFlow<List<HabitWithTodayProgress>> =
        dao.getAllHabits().map { habits ->
            val now = System.currentTimeMillis()
            val dayStart = startOfDay(now)
            val dayEnd = dayStart + 24 * 60 * 60 * 1000

            val logsToday = dao.getLogsBetween(dayStart, dayEnd)
                .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
                .value

            habits.map { habit ->
                val count = logsToday.filter { it.habitId == habit.id }.sumOf { it.value }
                HabitWithTodayProgress(habit, count)
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allLogs: StateFlow<List<HabitLog>> =
        dao.getAllLogs()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun addOrUpdateHabit(
        id: Long? = null,
        name: String,
        type: HabitType,
        targetValue: Int?,
        category: String?
    ) {
        viewModelScope.launch {
            val habit = Habit(
                id = id ?: 0,
                name = name,
                type = type,
                targetValue = targetValue,
                category = category
            )
            dao.upsertHabit(habit)
        }
    }

    fun deleteHabit(habit: Habit) {
        viewModelScope.launch {
            dao.deleteHabit(habit)
        }
    }

    fun logHabit(habit: Habit, value: Int = 1) {
        viewModelScope.launch {
            val log = HabitLog(
                habitId = habit.id,
                timestamp = System.currentTimeMillis(),
                value = value
            )
            dao.insertLog(log)
        }
    }

    private fun startOfDay(timeMillis: Long): Long {
        val oneDay = 24 * 60 * 60 * 1000L
        return timeMillis - (timeMillis % oneDay)
    }
}
