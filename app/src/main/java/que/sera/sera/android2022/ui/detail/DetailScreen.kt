package que.sera.sera.android2022.ui.detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.android.material.composethemeadapter3.Mdc3Theme
import que.sera.sera.android2022.model.todo.ToDo
import java.time.LocalDateTime

@ExperimentalMaterial3Api
@Composable
fun DetailScreen(
    viewModel: DetailViewModel,
    navController: NavController,
    toDoId: Int
) {
    DisposableEffect(Unit) {
        val job = viewModel.fetchInitialToDo(toDoId)
        onDispose { job.cancel() }
    }

    val uiState = viewModel.uiState.collectAsState()

    when (val currentState = uiState.value) {
        is DetailViewModelState.Loading ->
            // TODO: 読み込み中画面作る
            Text("読込中")
        is DetailViewModelState.Input -> ReminderRegister(
            initialToDoText = currentState.toDo.name,
            onRegister = { toDo ->
                viewModel.upsertToDo(toDo)
                navController.popBackStack()
            },
            onCancel = {
                navController.popBackStack()
            }
        )
    }
}

@Composable
fun ReminderRegister(
    modifier: Modifier = Modifier,
    initialToDo: ToDo,
    onRegister: (ToDo) -> Unit,
    onCancel: () -> Unit
) {
    var toDoText: TextFieldValue by remember {
        mutableStateOf(
            TextFieldValue(
                text = initialToDo.name,
                selection = TextRange(initialToDo.name.length)
            )
        )
    }
    val focusRequester = remember { FocusRequester() }

    fun changedToDo(): ToDo = initialToDo.copy(
        name = toDoText.text,
        updated = LocalDateTime.now()
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "やること",
            modifier = modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = toDoText,
            onValueChange = { toDoText = it },
            modifier = modifier
                .fillMaxWidth()
                .focusRequester(focusRequester),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { onRegister(changedToDo()) }
            )
        )

        Spacer(modifier = modifier.height(16.dp))

        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            OutlinedButton(onClick = { onCancel() }) {
                Text(text = "キャンセル")
            }

            Button(onClick = { onRegister(changedToDo()) }) {
                Text(text = if (initialToDo.id == 0) "登録する" else "更新する")
            }
        }
    }

    DisposableEffect(Unit) {
        focusRequester.requestFocus()
        onDispose { }
    }
}

@Preview
@Composable
fun PreviewReminderRegister() {
    Mdc3Theme {
        ReminderRegister(
            initialToDo = ToDo(),
            onRegister = { },
            onCancel = { }
        )
    }
}
