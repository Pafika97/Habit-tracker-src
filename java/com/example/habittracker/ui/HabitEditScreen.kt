package com.example.habittracker.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.habittracker.HabitViewModel
import com.example.habittracker.data.HabitType

@Composable
fun HabitEditScreen(
    viewModel: HabitViewModel,
    onDone: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var type by remember { mutableStateOf(HabitType.BINARY) }
    var targetValueText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Новая привычка", style = MaterialTheme.typography.titleLarge)

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Название привычки") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = category,
            onValueChange = { category = it },
            label = { Text("Категория (спорт, питание, гигиена...)") },
            modifier = Modifier.fillMaxWidth()
        )

        Text("Тип привычки", style = MaterialTheme.typography.bodyMedium)
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            FilterChip(
                selected = type == HabitType.BINARY,
                onClick = { type = HabitType.BINARY },
                label = { Text("Сделал / не сделал") }
            )
            FilterChip(
                selected = type == HabitType.QUANTITATIVE,
                onClick = { type = HabitType.QUANTITATIVE },
                label = { Text("Количество (минуты, стаканы...)") }
            )
        }

        if (type == HabitType.QUANTITATIVE) {
            OutlinedTextField(
                value = targetValueText,
                onValueChange = { targetValueText = it.filter { ch -> ch.isDigit() } },
                label = { Text("Цель (например, 30 минут)") },
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                if (name.isNotBlank()) {
                    val target = targetValueText.toIntOrNull()
                    viewModel.addOrUpdateHabit(
                        name = name,
                        type = type,
                        targetValue = target,
                        category = category.ifBlank { null }
                    )
                    onDone()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Сохранить")
        }
    }
}
