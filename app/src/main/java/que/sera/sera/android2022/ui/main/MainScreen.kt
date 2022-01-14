package que.sera.sera.android2022.ui.main

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import que.sera.sera.android2022.R
import que.sera.sera.android2022.model.todo.ToDo
import que.sera.sera.android2022.model.todo.ToDoStatus
import java.time.LocalDateTime

@Composable
@SuppressLint("ModifierParameter")
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = viewModel()
) {
    Scaffold(
        topBar = { AppBar() },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.addTodo() }
            ) {
                Icon(Icons.Filled.Add, contentDescription = "追加")
            }
        },
        content = {
            ToDoListView(
                listItems = viewModel.getToDos(),
                modifier = modifier
            )
        }
    )
}

@Preview
@Composable
fun AppBar(
    modifier: Modifier = Modifier
) = TopAppBar(
    modifier = modifier,
    title = { Text(stringResource(id = R.string.app_name)) }
)

@Preview
@Composable
@SuppressLint("ModifierParameter")
fun ToDoListView(
    modifier: Modifier = Modifier,
    listItems: Flow<List<ToDo>> = emptyFlow()
) {
    val listItemsState = listItems.collectAsState(emptyList())

    LazyColumn() {
        items(listItemsState.value) { ToDoListItem(listItem = it) }
    }
}

private val testData =
    ToDo(0, "テスト", ToDoStatus.Incomplete, LocalDateTime.now(), LocalDateTime.now())

@Preview
@Composable
@SuppressLint("ModifierParameter")
fun ToDoListItem(
    listItem: ToDo = testData,
    modifier: Modifier = Modifier
) {
    Column {
        Text(
            text = listItem.name,
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
        Divider()
    }
}