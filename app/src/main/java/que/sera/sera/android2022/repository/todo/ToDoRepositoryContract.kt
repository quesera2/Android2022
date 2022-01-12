package que.sera.sera.android2022.repository.todo

import que.sera.sera.android2022.model.todo.ToDo

interface ToDoRepositoryContract {

    suspend fun getTodos(): List<ToDo>

    suspend fun registerTodo(todo: ToDo)
}