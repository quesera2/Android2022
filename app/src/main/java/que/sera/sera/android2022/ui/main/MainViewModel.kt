package que.sera.sera.android2022.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import que.sera.sera.android2022.entity.ToDo
import que.sera.sera.android2022.entity.ToDoStatus

import que.sera.sera.android2022.data.repository.pref.PreferencesRepository
import que.sera.sera.android2022.data.repository.todo.ToDoRepository
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class MainViewModel @Inject constructor(
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

    fun doneToDo(toDo: ToDo) = viewModelScope.launch {
        toDo.copy(
            status = ToDoStatus.Completed
        ).also {
            toDoRepository.updateToDo(it)
        }
    }
}