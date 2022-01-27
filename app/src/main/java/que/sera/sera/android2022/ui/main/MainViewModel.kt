package que.sera.sera.android2022.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import que.sera.sera.android2022.model.todo.ToDo
import que.sera.sera.android2022.model.todo.ToDoStatus
import que.sera.sera.android2022.repository.pref.PreferencesRepository
import que.sera.sera.android2022.repository.todo.ToDoRepository
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class MainViewModel @Inject constructor(
    private val toDoRepository: ToDoRepository,
    private val prefRepository: PreferencesRepository,
) : ViewModel() {
    fun getToDos(): Flow<List<ToDo>> = prefRepository
        .showCompletedTask
        .flatMapLatest { showCompleteTask ->
            toDoRepository.getToDos(showCompleteTask)
        }

    fun doneToDo(toDo: ToDo) = viewModelScope.launch {
        toDo.copy(
            status = ToDoStatus.Completed
        ).also {
            toDoRepository.updateToDo(it)
        }
    }
}