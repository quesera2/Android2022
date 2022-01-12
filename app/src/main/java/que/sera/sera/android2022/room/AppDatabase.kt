package que.sera.sera.android2022.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import que.sera.sera.android2022.model.todo.ToDo

@Database(entities = [ToDo::class], version = 1)
@TypeConverters(Converter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun toDoDao(): ToDoDao
}