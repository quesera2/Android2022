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
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val toDoRepository: ToDoRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<DetailViewModelState>(DetailViewModelState.Loading)
    val uiState: StateFlow<DetailViewModelState> = _uiState

    fun getInitialToDo(id: Int) = viewModelScope.launch {
        val result =
            if (id == 0) ToDo()
            else toDoRepository.getToDo(id) ?: throw IllegalArgumentException()
        //TODO: ボトムシートとキーボードを同時に表示できないワークアラウンド
        delay(200)
        _uiState.value = DetailViewModelState.Input(result)
    }

    fun upsertToDo(toDo: ToDo) = viewModelScope.launch {
        if (toDo.id == 0) {
            toDoRepository.registerToDo(toDo)
        } else {
            toDoRepository.updateToDo(toDo)
        }
    }
}