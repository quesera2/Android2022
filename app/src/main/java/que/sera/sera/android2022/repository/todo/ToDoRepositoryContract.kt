package que.sera.sera.android2022.repository.todo

import kotlinx.coroutines.flow.Flow
import que.sera.sera.android2022.model.todo.ToDo

interface ToDoRepositoryContract {

    fun getTodos(): Flow<List<ToDo>>

    fun registerTodo(todo: ToDo)
}