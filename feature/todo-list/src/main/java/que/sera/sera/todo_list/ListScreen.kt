package que.sera.sera.todo_list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import que.sera.sera.core_ui.AppTheme
import que.sera.sera.todo.entity.ToDo
import que.sera.sera.todo.entity.ToDoStatus

@ExperimentalMaterial3Api
@Composable
fun ListScreen(
    viewModel: ListViewModel,
    createToDo: () -> Unit,
    editToDo: (id: Int) -> Unit,
) {
    LaunchedEffect(Unit) {
        viewModel.fetchToDos()
    }

    Scaffold(
        modifier = Modifier.systemBarsPadding(),
        topBar = {
            val showTaskCompleted by viewModel.showCompletedTask.collectAsState()
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
                onClick = createToDo
            ) {
                Icon(Icons.Filled.Add, contentDescription = "追加")
            }
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                val uiState by viewModel.uiState.collectAsState()

                ToDoListContent(
                    listItems = uiState.items,
                    showProgress = uiState.showProgress,
                    onChecked = { todo, completed -> viewModel.updateTask(todo, completed) },
                    onClick = { editToDo(it.id) },
                )
            }
        }
    )
}

@Composable
private fun ToDoListContent(
    listItems: Flow<List<ToDo>>,
    showProgress: Boolean,
    onClick: (ToDo) -> Unit,
    onChecked: (ToDo, Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        if (showProgress) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            ToDoListView(
                listItems = listItems,
                onClick = onClick,
                onChecked = onChecked,
                modifier = Modifier
                    .fillMaxSize()
            )
        }
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ToDoListView(
    listItems: Flow<List<ToDo>>,
    onClick: (ToDo) -> Unit,
    onChecked: (ToDo, Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    val toDos by listItems.collectAsState(initial = emptyList())

    LazyColumn(
        modifier = modifier
    ) {
        items(
            items = toDos,
            key = { it.id }
        ) { item ->
            ToDoListItem(
                toDo = item,
                onClick = { onClick(item) },
                onChecked = { onChecked(item, it) },
                modifier = Modifier.animateItemPlacement()
            )
        }
    }
}

private class ToDoListItemPreviewProvider : PreviewParameterProvider<Pair<List<ToDo>, Boolean>> {
    private val listItems = listOf(
        ToDo(
            0,
            "完了",
            ToDoStatus.Completed
        ),
        ToDo(
            1,
            "未完了",
            ToDoStatus.Incomplete
        )
    )

    override val values: Sequence<Pair<List<ToDo>, Boolean>> = sequenceOf(
        listItems to false,
        emptyList<ToDo>() to true
    )

}

@Preview(showBackground = true)
@Composable
private fun PreviewToDoListItem(
    @PreviewParameter(ToDoListItemPreviewProvider::class) state: Pair<List<ToDo>, Boolean>
) {
    AppTheme {
        ToDoListContent(
            listItems = flowOf(state.first),
            showProgress = state.second,
            onChecked = { _, _ -> },
            onClick = {}
        )
    }
}