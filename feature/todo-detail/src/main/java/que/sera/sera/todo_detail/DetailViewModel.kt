package que.sera.sera.todo_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import que.sera.sera.android2022.data.repository.todo.ToDoRepository
import que.sera.sera.android2022.entity.ToDo
import java.util.Date
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
        withContext(NonCancellable) {
            val newToDo = ToDo(name = toDoText)
            toDoRepository.registerToDo(newToDo)
            //TODO: 登録処理後に閉じるように処理を変える
        }
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