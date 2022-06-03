package que.sera.sera.android2022.ui.detail

import que.sera.sera.android2022.data.entity.todo.ToDo

sealed interface DetailViewModelState {

    object Loading : DetailViewModelState

    object InputInitial : DetailViewModelState

    data class InputEdit(val toDo: ToDo) : DetailViewModelState

}
