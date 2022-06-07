package que.sera.sera.android2022.data.repository.todo

import kotlinx.coroutines.flow.Flow
import que.sera.sera.android2022.entity.ToDo

interface ToDoRepository {

    fun getToDos(showComplete: Boolean): Flow<List<ToDo>>

    suspend fun getToDo(id: Int): ToDo?

    suspend fun registerToDo(todo: ToDo)

    suspend fun updateToDo(todo: ToDo)
}