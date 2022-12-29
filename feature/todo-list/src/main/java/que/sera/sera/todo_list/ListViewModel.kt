package que.sera.sera.todo_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import que.sera.sera.todo.entity.ToDo
import que.sera.sera.todo.entity.ToDoStatus
import que.sera.sera.todo.repository.pref.PreferencesRepository
import que.sera.sera.todo.repository.todo.ToDoRepository
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class ListViewModel @Inject constructor(
    private val toDoRepository: ToDoRepository,
    private val prefRepository: PreferencesRepository,
) : ViewModel() {

    val showCompletedTask: Flow<Boolean>
        get() = prefRepository
            .showCompletedTask
            .stateIn(viewModelScope, SharingStarted.Lazily, true)

    private val _showProgress: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val showProgress: Flow<Boolean>
        get() = _showProgress.asStateFlow()

    init {
        viewModelScope.launch {
            getToDos().collect {
                _showProgress.value = false
            }
        }
    }

    fun getToDos(): Flow<List<ToDo>> = showCompletedTask
        .flatMapLatest { showCompleteTask ->
            toDoRepository.getToDos(showCompleteTask)
        }

    fun updateShowCompleteTask(newValue: Boolean) = viewModelScope.launch {
        prefRepository.updateShowCompletedTask(newValue)
    }

    fun updateTask(toDo: ToDo, complete: Boolean) = viewModelScope.launch {
        val newStatus = if (complete) ToDoStatus.Completed else ToDoStatus.Incomplete
        val updatedItem = toDo.copy(
            status = newStatus
        )
        toDoRepository.updateToDo(updatedItem)
    }
}