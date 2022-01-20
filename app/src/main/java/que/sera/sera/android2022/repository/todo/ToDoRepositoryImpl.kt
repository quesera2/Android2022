package que.sera.sera.android2022.repository.todo

import kotlinx.coroutines.flow.Flow
import que.sera.sera.android2022.model.todo.ToDo
import que.sera.sera.android2022.room.ToDoDao

class ToDoRepositoryImpl(
    private val dao: ToDoDao
) : ToDoRepository {

    override fun getToDos(): Flow<List<ToDo>> = dao.getAll()

    override fun getToDo(id: Int): ToDo? = dao.findById(id)

    override suspend fun registerToDo(todo: ToDo) = dao.insert(todo)

    override suspend fun updateToDo(todo: ToDo) = dao.update(todo)
}