package que.sera.sera.todo.entity.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ToDoDao {
    @Query("SELECT * FROM todo ORDER BY CASE status WHEN 'Incomplete' THEN 0 ELSE 1 END, updated DESC")
    fun getAll(): Flow<List<que.sera.sera.todo.entity.ToDo>>

    @Query("SELECT * FROM todo WHERE status == 'Incomplete' ORDER BY updated DESC")
    fun getIncomplete(): Flow<List<que.sera.sera.todo.entity.ToDo>>

    @Query("SELECT * FROM todo WHERE id = :id LIMIT 1")
    suspend fun findById(id: Int): que.sera.sera.todo.entity.ToDo?

    @Insert
    suspend fun insert(vararg todos: que.sera.sera.todo.entity.ToDo)

    @Update
    suspend fun update(vararg todos: que.sera.sera.todo.entity.ToDo)
}