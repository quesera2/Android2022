package que.sera.sera.android2022.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import que.sera.sera.android2022.model.todo.ToDo
import que.sera.sera.android2022.model.todo.ToDoStatus
import que.sera.sera.android2022.repository.todo.ToDoRepository
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val toDoRepository: ToDoRepository
) : ViewModel() {
    fun getToDos(): Flow<List<ToDo>> = toDoRepository.getToDos()

    fun addToDo() = viewModelScope.launch {
        ToDo(
            name = "テスト",
            status = ToDoStatus.Incomplete
        ).also {
            toDoRepository.registerToDo(it)
        }
    }

    fun doneToDo(toDo: ToDo) = viewModelScope.launch {
        toDo.copy(
            status = ToDoStatus.Completed
        ).also {
            toDoRepository.updateToDo(it)
        }
    }
}