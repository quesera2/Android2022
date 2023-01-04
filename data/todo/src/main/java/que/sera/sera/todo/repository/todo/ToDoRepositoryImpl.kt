package que.sera.sera.todo.repository.todo

import kotlinx.coroutines.flow.Flow
import que.sera.sera.todo.entity.ToDo
import que.sera.sera.todo.entity.room.ToDoDao

internal class ToDoRepositoryImpl(
    private val dao: ToDoDao
) : ToDoRepository {

    override fun fetchToDos(showComplete: Boolean): Flow<List<ToDo>> =
        if (showComplete) dao.getAll() else dao.getIncomplete()

    override suspend fun getToDo(id: Int): ToDo? = dao.findById(id)

    override suspend fun registerToDo(todo: ToDo) = dao.insert(todo)

    override suspend fun updateToDo(todo: ToDo) = dao.update(todo)
}