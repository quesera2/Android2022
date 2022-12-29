package que.sera.sera.todo.repository.todo

import kotlinx.coroutines.flow.Flow
import que.sera.sera.todo.entity.ToDo

interface ToDoRepository {

    fun getToDos(showComplete: Boolean): Flow<List<ToDo>>

    suspend fun getToDo(id: Int): ToDo?

    suspend fun registerToDo(todo: ToDo)

    suspend fun updateToDo(todo: ToDo)
}