package que.sera.sera.android2022.model.todo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@Entity
data class ToDo(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo
    val name: String = "",
    @ColumnInfo(index = true)
    val status: ToDoStatus = ToDoStatus.Incomplete,
    @ColumnInfo
    val updated: LocalDateTime = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS),
    @ColumnInfo
    val created: LocalDateTime = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS)
)