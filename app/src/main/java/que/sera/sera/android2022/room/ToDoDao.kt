package que.sera.sera.android2022.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import que.sera.sera.android2022.model.todo.ToDo

@Dao
interface ToDoDao {
    @Query("SELECT * FROM todo")
    fun getAll(): Flow<List<ToDo>>

    @Insert
    suspend fun insert(vararg todos: ToDo)
}