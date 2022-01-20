package que.sera.sera.android2022.ui.main

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import que.sera.sera.android2022.model.todo.ToDo
import que.sera.sera.android2022.model.todo.ToDoStatus
import que.sera.sera.android2022.ui.common.AppBar
import java.time.LocalDateTime

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
            ToDoListView(
                listItems = viewModel.getToDos(),
                modifier = modifier,
                onClick = {
                    navController.navigate("detail/${it.id}")
                }
            )
        }
    )
}


@Preview
@Composable
@SuppressLint("ModifierParameter")
fun ToDoListView(
    modifier: Modifier = Modifier,
    listItems: Flow<List<ToDo>> = emptyFlow(),
    onClick: (ToDo) -> Unit = { }
) {
    val listItemsState = listItems.collectAsState(emptyList())

    LazyColumn {
        items(listItemsState.value) {
            ToDoListItem(
                listItem = it,
                onClick = onClick
            )
        }
    }
}

private val testData =
    ToDo(0, "テスト", ToDoStatus.Completed, LocalDateTime.now(), LocalDateTime.now())

@Preview
@Composable
@SuppressLint("ModifierParameter")
fun ToDoListItem(
    listItem: ToDo = testData,
    modifier: Modifier = Modifier,
    onClick: (ToDo) -> Unit = { }
) {
    Column(
        modifier = modifier.clickable { onClick(listItem) }
    ) {
        Row(
            modifier = modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${listItem.name} id:${listItem.id}",
                color = when (listItem.status) {
                    ToDoStatus.Incomplete -> Color.DarkGray
                    ToDoStatus.Completed -> Color.Gray
                },
                style = when (listItem.status) {
                    ToDoStatus.Incomplete -> TextStyle.Default
                    ToDoStatus.Completed -> TextStyle(
                        textDecoration = TextDecoration.LineThrough,
                        color = Color.Gray
                    )
                }
            )
            Spacer(
                modifier = Modifier.weight(1f)
            )
            Icon(
                Icons.Filled.Check,
                contentDescription = "タスク状態",
                tint = when (listItem.status) {
                    ToDoStatus.Incomplete -> Color.Transparent
                    ToDoStatus.Completed -> Color.LightGray
                }
            )
        }
    }
}