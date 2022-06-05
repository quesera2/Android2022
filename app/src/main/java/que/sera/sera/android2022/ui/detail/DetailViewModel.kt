package que.sera.sera.android2022.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import que.sera.sera.android2022.entity.ToDo
import que.sera.sera.android2022.data.repository.todo.ToDoRepository
import java.util.*
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val toDoRepository: ToDoRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<DetailViewModelState>(DetailViewModelState.Loading)
    val uiState: StateFlow<DetailViewModelState> = _uiState

    fun fetchInitialToDo(id: Int) = viewModelScope.launch {
        if (id == 0) {
            _uiState.value = DetailViewModelState.InputInitial
        } else {
            val result = toDoRepository.getToDo(id) ?: throw IllegalArgumentException()
            _uiState.value = DetailViewModelState.InputEdit(result)
        }
    }

    fun registerToDo(toDoText: String) = viewModelScope.launch {
        ToDo(name = toDoText).let { toDoRepository.registerToDo(it) }
    }

    fun updateToDo(toDoText: String) {
        val state = uiState.value
        if (state !is DetailViewModelState.InputEdit) {
            throw IllegalStateException()
        }

        viewModelScope.launch {
            state.toDo.copy(
                name = toDoText,
//                updated = LocalDateTime.now()
                updated = Date()
            ).let {
                toDoRepository.updateToDo(it)
            }
        }
    }
}