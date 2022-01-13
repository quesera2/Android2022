package que.sera.sera.android2022.ui.main

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import que.sera.sera.android2022.model.todo.ToDo
import que.sera.sera.android2022.repository.todo.ToDoRepository
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val toDoRepository: ToDoRepository
) : ViewModel() {
    fun getToDos(): Flow<List<ToDo>> = toDoRepository.getTodos()
}