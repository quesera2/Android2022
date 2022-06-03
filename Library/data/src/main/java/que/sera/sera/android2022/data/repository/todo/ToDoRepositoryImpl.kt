package que.sera.sera.android2022.data.repository.todo

import kotlinx.coroutines.flow.Flow
import que.sera.sera.android2022.data.entity.todo.ToDo
import que.sera.sera.android2022.data.room.ToDoDao

class ToDoRepositoryImpl(
    private val dao: ToDoDao
) : ToDoRepository {

    override fun getToDos(showComplete: Boolean): Flow<List<ToDo>> =
        if (showComplete) dao.getAll() else dao.getIncomplete()

    override suspend fun getToDo(id: Int): ToDo? = dao.findById(id)

    override suspend fun registerToDo(todo: ToDo) = dao.insert(todo)

    override suspend fun updateToDo(todo: ToDo) = dao.update(todo)
}