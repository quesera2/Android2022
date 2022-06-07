package que.sera.sera.android2022.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class ToDo(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo
    val name: String = "",
    @ColumnInfo(index = true)
    val status: ToDoStatus = ToDoStatus.Incomplete,
    @ColumnInfo
    val updated: Date = Date(),
    @ColumnInfo
    val created: Date = Date()
)