package que.sera.sera.android2022.ui.main

import android.annotation.SuppressLint
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.android.material.composethemeadapter3.Mdc3Theme
import que.sera.sera.android2022.model.todo.ToDo
import que.sera.sera.android2022.model.todo.ToDoStatus
import que.sera.sera.android2022.ui.common.AppBar
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*


@ExperimentalMaterial3Api
@Composable
@SuppressLint("ModifierParameter")
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel,
    navController: NavController,
) {
    Scaffold(
        topBar = { AppBar() },
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


@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
@SuppressLint("ModifierParameter")
fun ToDoListView(
    modifier: Modifier = Modifier,
    listItems: List<ToDo>,
    onClick: (ToDo) -> Unit = { },
    onSwipe: (ToDo) -> Unit = { },
) {
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
                    ToDoListItem(
                        listItem = item,
                        onClick = onClick,
                    )
                }
            )
        }
    }
}

@Composable
@SuppressLint("ModifierParameter")
fun ToDoListItem(
    modifier: Modifier = Modifier,
    listItem: ToDo,
    onClick: (ToDo) -> Unit = { },
) {
    Surface {
        Row(
            modifier = modifier
                .clickable { onClick(listItem) }
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = listItem.name,
                    color = when (listItem.status) {
                        ToDoStatus.Incomplete -> MaterialTheme.colorScheme.onSurface
                        ToDoStatus.Completed -> MaterialTheme.colorScheme.secondary
                    },
                    fontWeight = FontWeight.Bold,
                    style = when (listItem.status) {
                        ToDoStatus.Incomplete -> TextStyle.Default
                        ToDoStatus.Completed -> TextStyle(
                            textDecoration = TextDecoration.LineThrough,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                )
                Text(
                    text = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
                        .withLocale(Locale.getDefault())
                        .format(listItem.updated),
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
                tint = when (listItem.status) {
                    ToDoStatus.Incomplete -> Color.Transparent
                    ToDoStatus.Completed -> MaterialTheme.colorScheme.secondary
                }
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