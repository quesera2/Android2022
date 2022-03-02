package que.sera.sera.android2022.ui.main

import android.annotation.SuppressLint
import android.text.format.DateFormat
import android.text.format.DateUtils
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.android.material.composethemeadapter3.Mdc3Theme
import que.sera.sera.android2022.R
import que.sera.sera.android2022.model.todo.ToDo
import que.sera.sera.android2022.model.todo.ToDoStatus
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle


@ExperimentalMaterial3Api
@Composable
@SuppressLint("ModifierParameter")
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel,
    navController: NavController,
) {
    val scrollBehavior = remember { TopAppBarDefaults.enterAlwaysScrollBehavior() }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            val showTaskCompleted = viewModel.showCompletedTask.collectAsState(initial = false)
            AppBar(
                scrollBehavior = scrollBehavior,
                showCompletedTask = showTaskCompleted.value,
                onClick = { viewModel.updateShowCompleteTask(it) })
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
        content = {
            val listItemsState = viewModel.getToDos().collectAsState(emptyList())
            ToDoListView(
                listItems = listItemsState.value,
                modifier = modifier,
                onClick = {
                    navController.navigate("detail/${it.id}")
                },
                onSwipe = {
                    viewModel.doneToDo(it)
                }
            )
        }
    )
}

@Composable
fun AppBar(
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior,
    showCompletedTask: Boolean,
    onClick: (Boolean) -> Unit
) {
    SmallTopAppBar(
        modifier = modifier,
        title = { Text(text = "タスク一覧") },
        actions = {
            IconButton(onClick = { onClick(!showCompletedTask) }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_filter_list_24),
                    contentDescription = "タスク完了表示を切り替える",
                    tint = if (showCompletedTask) MaterialTheme.colorScheme.primary.copy(0.4f)
                    else MaterialTheme.colorScheme.primary
                )
            }
        },
        scrollBehavior = scrollBehavior
    )
}


@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
@SuppressLint("ModifierParameter")
fun ToDoListView(
    modifier: Modifier = Modifier,
    listItems: List<ToDo>,
    onClick: (ToDo) -> Unit = { },
    onSwipe: (ToDo) -> Unit = { },
) {
    val locale = LocalContext.current.resources.configuration.locales[0]
//    val formatter = DateTimeFormatter
//        .ofLocalizedDateTime(FormatStyle.MEDIUM)
//        .withLocale(locale)
    val formatter = SimpleDateFormat(DateFormat.getBestDateTimePattern(locale, "yyyyMMMdd"), locale)

    LazyColumn {
        items(listItems, key = { it.id }) { item ->
            val dismissState = rememberDismissState(
                confirmStateChange = {
                    if (item.status == ToDoStatus.Incomplete && it == DismissValue.DismissedToEnd) {
                        onSwipe(item)
                        return@rememberDismissState true
                    }
                    false
                }
            )
            if (dismissState.currentValue != DismissValue.Default) {
                LaunchedEffect(Unit) {
                    dismissState.snapTo(DismissValue.Default)
                }
            }

            SwipeToDismiss(
                state = dismissState,
                modifier = Modifier.animateItemPlacement(),
                background = {
                    val color by animateColorAsState(
                        when (dismissState.targetValue) {
                            DismissValue.Default -> MaterialTheme.colorScheme.background
                            DismissValue.DismissedToEnd -> Color.Green
                            DismissValue.DismissedToStart -> MaterialTheme.colorScheme.background
                        }
                    )
                    val scale by animateFloatAsState(
                        if (dismissState.targetValue == DismissValue.Default) 0.75f else 1f
                    )

                    Box(
                        Modifier
                            .fillMaxSize()
                            .background(color)
                            .padding(horizontal = 20.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Icon(
                            Icons.Default.Done,
                            contentDescription = "check mark",
                            modifier = Modifier.scale(scale)
                        )
                    }
                },
                dismissContent = {
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
            )
        }
    }
}

@Composable
@SuppressLint("ModifierParameter")
fun InCompleteToDoListItem(
    modifier: Modifier = Modifier,
    toDo: ToDo,
    formatter: SimpleDateFormat,
    onClick: (ToDo) -> Unit
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
@SuppressLint("ModifierParameter")
fun CompletedToDoListItem(
    modifier: Modifier = Modifier,
    formatter: SimpleDateFormat,
    toDo: ToDo
) {
    Surface {
        Row(
            modifier = modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
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
            Spacer(
                modifier = Modifier.weight(1f)
            )
            Icon(
                Icons.Filled.Check,
                contentDescription = "タスク状態",
                tint = MaterialTheme.colorScheme.secondary
            )
        }
    }
}

@Preview
@Composable
fun PreviewToDoList() {
    Mdc3Theme {
        ToDoListView(
            listItems = listOf(
                ToDo(0, "完了", ToDoStatus.Completed),
                ToDo(0, "未完了", ToDoStatus.Incomplete),
            )
        )
    }
}