package que.sera.sera.android2022.ui.detail

import que.sera.sera.android2022.model.todo.ToDo

sealed interface DetailViewModelState {

    object Loading : DetailViewModelState

    data class Input(val toDo: ToDo) : DetailViewModelState

}
