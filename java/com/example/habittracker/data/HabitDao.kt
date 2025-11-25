package com.example.habittracker.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitDao {

    @Query("SELECT * FROM habits WHERE isActive = 1 ORDER BY id DESC")
    fun getAllHabits(): Flow<List<Habit>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertHabit(habit: Habit): Long

    @Delete
    suspend fun deleteHabit(habit: Habit)

    @Query("SELECT * FROM habits WHERE id = :habitId LIMIT 1")
    suspend fun getHabitById(habitId: Long): Habit?

    @Query("SELECT * FROM habit_logs WHERE habitId = :habitId ORDER BY timestamp DESC")
    fun getLogsForHabit(habitId: Long): Flow<List<HabitLog>>

    @Query("SELECT * FROM habit_logs ORDER BY timestamp DESC")
    fun getAllLogs(): Flow<List<HabitLog>>

    @Insert
    suspend fun insertLog(log: HabitLog)

    @Query("""
        SELECT * FROM habit_logs
        WHERE timestamp BETWEEN :from AND :to
        ORDER BY timestamp ASC
    """)
    fun getLogsBetween(from: Long, to: Long): Flow<List<HabitLog>>
}
