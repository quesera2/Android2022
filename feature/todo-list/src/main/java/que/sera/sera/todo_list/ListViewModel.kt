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

    val showCompletedTask: StateFlow<Boolean> = prefRepository
        .showCompletedTask
        .stateIn(viewModelScope, SharingStarted.Lazily, true)

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState>
        get() = _uiState.asStateFlow()

    fun fetchToDos() = viewModelScope.launch {
        _uiState.run { emit(value.onShowProgress()) }

        val items = showCompletedTask.flatMapLatest(toDoRepository::fetchToDos)

        _uiState.run { emit(value.onFetchItems(items)) }
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