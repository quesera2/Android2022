package que.sera.sera.todo_list

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import que.sera.sera.todo.entity.ToDo

data class UiState(
    val items: Flow<List<ToDo>> = emptyFlow(),
    val showProgress: Boolean = false,
) {
    fun onShowProgress(): UiState = copy(
        showProgress = true
    )

    fun onFetchItems(
        items: Flow<List<ToDo>>
    ): UiState = copy(
        showProgress = false,
        items = items
    )
}