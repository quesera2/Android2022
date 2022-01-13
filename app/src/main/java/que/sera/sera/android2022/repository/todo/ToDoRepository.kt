package que.sera.sera.android2022.repository.todo

import kotlinx.coroutines.flow.Flow
import que.sera.sera.android2022.model.todo.ToDo
import que.sera.sera.android2022.room.AppDatabase

class ToDoRepository(
    private val database: AppDatabase
) : ToDoRepositoryContract {

    override fun getTodos(): Flow<List<ToDo>> = database.toDoDao().getAll()

    override fun registerTodo(todo: ToDo) = database.toDoDao().insert(todo)
}