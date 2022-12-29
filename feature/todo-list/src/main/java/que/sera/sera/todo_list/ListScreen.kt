package que.sera.sera.todo_list

import android.text.format.DateFormat
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import que.sera.sera.android2022.entity.ToDo
import que.sera.sera.android2022.entity.ToDoStatus
import que.sera.sera.core_ui.AppTheme
import java.text.SimpleDateFormat

@ExperimentalMaterial3Api
@Composable
fun MainScreen(
    viewModel: ListViewModel,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = Modifier
            .systemBarsPadding(),
        topBar = {
            val showTaskCompleted by viewModel.showCompletedTask.collectAsState(initial = false)
            ListAppBar(
                showCompletedTask = showTaskCompleted,
                onClick = { viewModel.updateShowCompleteTask(it) },
                modifier = Modifier.windowInsetsPadding(
                    WindowInsets.systemBars.only(WindowInsetsSides.Horizontal + WindowInsetsSides.Top)
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate("detail/0")
                }
            ) {
                Icon(Icons.Filled.Add, contentDescription = "追加")
            }
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                val listItemsState by viewModel.getToDos().collectAsState(emptyList())
                ToDoListView(
                    listItems = listItemsState,
                    modifier = modifier.padding(paddingValues = paddingValues),
                    onClick = {
                        navController.navigate("detail/${it.id}")
                    },
                )

                val showProgress by viewModel.showProgress.collectAsState(initial = true)
                if (showProgress) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    )
}


@Composable
fun ToDoListView(
    listItems: List<ToDo>,
    modifier: Modifier = Modifier,
    onClick: (ToDo) -> Unit = { },
) {
    val locale = LocalContext.current.resources.configuration.locales[0]
    val formatter = SimpleDateFormat(DateFormat.getBestDateTimePattern(locale, "yyyyMMMdd"), locale)

    LazyColumn(
        modifier = modifier
    ) {
        items(listItems, key = { it.id }) { item ->
            when (item.status) {
                ToDoStatus.Incomplete -> InCompleteToDoListItem(
                    toDo = item,
                    formatter = formatter,
                    onClick = onClick
                )

                ToDoStatus.Completed -> CompletedToDoListItem(
                    toDo = item,
                    formatter = formatter
                )
            }
        }
    }
}

@Composable
fun InCompleteToDoListItem(
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
fun CompletedToDoListItem(
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

@Preview(showBackground = true)
@Composable
fun PreviewToDoList() {
    AppTheme {
        ToDoListView(
            listItems = listOf(
                ToDo(
                    0,
                    "完了",
                    ToDoStatus.Completed
                ),
                ToDo(
                    1,
                    "未完了",
                    ToDoStatus.Incomplete
                ),
            )
        )
    }
}
