package que.sera.sera.todo_list

import android.text.format.DateFormat
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import que.sera.sera.core_ui.AppTheme
import que.sera.sera.todo.entity.ToDo
import que.sera.sera.todo.entity.ToDoStatus
import java.text.SimpleDateFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ToDoListItem(
    toDo: ToDo,
    onClick: () -> Unit,
    onChecked: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val locale = remember(context) { context.resources.configuration.locales[0] }
    val formatter = remember(locale) {
        SimpleDateFormat(DateFormat.getBestDateTimePattern(locale, "yyyyMMMdd"), locale)
    }

    var isCompleted: Boolean by remember(toDo) {
        mutableStateOf(toDo.status == ToDoStatus.Incomplete)
    }

    ListItem(
        modifier = modifier.clickable(
            enabled = isCompleted,
            onClick = onClick
        ),
        headlineText = {
            HeadLineText(
                text = toDo.name,
                isCompleted = isCompleted
            )
        },
        supportingText = {
            Text(
                text = formatter.format(toDo.updated),
                color = MaterialTheme.colorScheme.tertiary,
                fontSize = MaterialTheme.typography.labelSmall.fontSize
            )
        },
        leadingContent = {
            Checkbox(
                checked = !isCompleted,
                onCheckedChange = {
                    isCompleted = !it
                    onChecked(it)
                }
            )
        }
    )
}

@Composable
private fun HeadLineText(
    text: String,
    isCompleted: Boolean
) {
    val (textColor, textDecoration) = if (isCompleted) {
        Color.Companion.Unspecified to TextDecoration.None
    } else {
        MaterialTheme.colorScheme.secondary to TextDecoration.LineThrough
    }

    Box {
        AnimatedVisibility(
            visible = !isCompleted,
            enter = fadeIn(tween()),
            exit = fadeOut(tween())
        ) {
            Text(
                text = text,
                color = textColor,
                textDecoration = textDecoration,
            )
        }
        AnimatedVisibility(
            visible = isCompleted,
            enter = fadeIn(tween()),
            exit = fadeOut(tween())
        ) {
            Text(
                text = text,
                color = textColor,
                textDecoration = textDecoration,
            )
        }
    }
}

@Preview
@Composable
private fun PreviewToDoListItem() {
    AppTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ToDoListItem(
                toDo = ToDo(
                    name = "未完了",
                ),
                onClick = {},
                onChecked = {},
            )
            ToDoListItem(
                toDo = ToDo(
                    name = "完了",
                    status = ToDoStatus.Completed,
                ),
                onClick = {},
                onChecked = {},
            )
        }
    }
}