package que.sera.sera.android2022.model.todo

import java.time.LocalDateTime

data class ToDo(
    val name: String = "",
    val status: ToDoStatus = ToDoStatus.Incomplete,
    val updated: LocalDateTime = LocalDateTime.now(),
    val created: LocalDateTime = LocalDateTime.now()
)