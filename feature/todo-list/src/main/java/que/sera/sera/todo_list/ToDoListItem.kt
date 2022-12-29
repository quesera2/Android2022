package que.sera.sera.todo_list

import android.text.format.DateFormat
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import que.sera.sera.todo.entity.ToDo
import que.sera.sera.todo.entity.ToDoStatus
import java.text.SimpleDateFormat

@Composable
internal fun ToDoListItem(
    toDo: ToDo,
    onClick: (ToDo) -> Unit,
) {
    val context = LocalContext.current
    val locale = remember(context) { context.resources.configuration.locales[0] }
    val formatter = remember(locale) {
        SimpleDateFormat(DateFormat.getBestDateTimePattern(locale, "yyyyMMMdd"), locale)
    }

    when (toDo.status) {
        ToDoStatus.Incomplete -> InCompleteToDoListItem(
            toDo = toDo,
            formatter = formatter,
            onClick = onClick
        )

        ToDoStatus.Completed -> CompletedToDoListItem(
            toDo = toDo,
            formatter = formatter
        )
    }
}

@Composable
private fun InCompleteToDoListItem(
    toDo: ToDo,
    formatter: SimpleDateFormat,
    onClick: (ToDo) -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface {
        Column(
            modifier = modifier
                .clickable { onClick(toDo) }
                .padding(16.dp)
                .fillMaxWidth(),
        ) {
            Text(
                text = toDo.name,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = formatter.format(toDo.updated),
                color = MaterialTheme.colorScheme.tertiary,
                fontSize = MaterialTheme.typography.labelSmall.fontSize
            )
        }
    }
}

@Composable
private fun CompletedToDoListItem(
    formatter: SimpleDateFormat,
    toDo: ToDo,
    modifier: Modifier = Modifier,
) {
    Surface {
        Row(
            modifier = modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1.0f)
            ) {
                Text(
                    text = toDo.name,
                    color = MaterialTheme.colorScheme.secondary,
                    style = TextStyle(
                        textDecoration = TextDecoration.LineThrough,
                        color = MaterialTheme.colorScheme.secondary

                    )
                )
                Text(
                    text = formatter.format(toDo.updated),
                    color = MaterialTheme.colorScheme.tertiary,
                    fontSize = MaterialTheme.typography.labelSmall.fontSize
                )
            }
            Icon(
                Icons.Filled.Check,
                contentDescription = "タスク状態",
                tint = MaterialTheme.colorScheme.secondary
            )
        }
    }
}

