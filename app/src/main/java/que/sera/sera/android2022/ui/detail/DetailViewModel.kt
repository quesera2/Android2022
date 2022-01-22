package que.sera.sera.android2022.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import que.sera.sera.android2022.model.todo.ToDo
import que.sera.sera.android2022.repository.todo.ToDoRepository
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val toDoRepository: ToDoRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<DetailViewModelState>(DetailViewModelState.Loading)
    val uiState: StateFlow<DetailViewModelState> = _uiState

    fun fetchInitialToDo(id: Int) = viewModelScope.launch {
        //TODO: ボトムシートとキーボードを同時に表示するためのワークアラウンド
        delay(200)
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
                updated = LocalDateTime.now()
            ).let {
                toDoRepository.updateToDo(it)
            }
        }
    }
}