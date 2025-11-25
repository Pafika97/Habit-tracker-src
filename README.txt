Habit Tracker Android App (Kotlin + Jetpack Compose + Room)
==========================================================

Это набор исходных файлов для приложения "Трекер привычек".
Эти файлы нужно вставить в созданный в Android Studio проект.

Кратко что делать:
1. В Android Studio создайте новый проект (Empty Activity / Empty Compose Activity)
   с пакетом com.example.habittracker.
2. Замените содержимое файла app/build.gradle на тот, что в этой папке.
3. В каталоге java/com/example/habittracker создайте такие же пакеты и вставьте .kt файлы:
   - HabitViewModel.kt
   - MainActivity.kt
   - data/... (Habit.kt, HabitLog.kt, HabitDao.kt, AppDatabase.kt)
   - ui/... (HabitListScreen.kt, HabitEditScreen.kt, StatsScreen.kt)
   - ui/theme/... (Theme.kt)
4. Нажмите Sync Now, затем соберите и запустите приложение.

Структура архива повторяет структуру пакетов.
