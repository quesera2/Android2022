package que.sera.sera.android2022.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import que.sera.sera.android2022.model.todo.ToDo

@Dao
interface ToDoDao {
    @Query("SELECT * FROM todo ORDER BY CASE status WHEN 'Incomplete' THEN 0 ELSE 1 END, updated DESC")
    fun getAll(): Flow<List<ToDo>>

    @Query("SELECT * FROM todo WHERE status == 'Incomplete' ORDER BY updated DESC")
    fun getIncomplete(): Flow<List<ToDo>>

    @Query("SELECT * FROM todo WHERE id = :id LIMIT 1")
    suspend fun findById(id: Int): ToDo?

    @Insert
    suspend fun insert(vararg todos: ToDo)

    @Update
    suspend fun update(vararg todos: ToDo)
}