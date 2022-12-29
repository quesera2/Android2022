package que.sera.sera.todo.entity.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import que.sera.sera.todo.entity.ToDo

@Database(entities = [ToDo::class], version = 1, exportSchema = false)
@TypeConverters(Converter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun toDoDao(): ToDoDao
}