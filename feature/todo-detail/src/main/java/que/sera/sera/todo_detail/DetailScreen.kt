package que.sera.sera.todo_detail

import android.content.res.Configuration.UI_MODE_NIGHT_MASK
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import que.sera.sera.core_ui.AppTheme

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

    Surface {

        val uiState = viewModel.uiState.collectAsState()
        val cancel: () -> Unit = { navController.popBackStack() }

        when (val currentState = uiState.value) {
            is DetailViewModelState.Loading -> ReminderRegister(
                inputEnabled = false,
                onCancel = cancel
            )

            is DetailViewModelState.InputInitial -> ReminderRegister(
                buttonLabel = "登録する",
                onRegister = { toDoText ->
                    viewModel.registerToDo(toDoText)
                    navController.popBackStack()
                },
                onCancel = cancel
            )

            is DetailViewModelState.InputEdit -> ReminderRegister(
                initialToDoText = currentState.toDo.name,
                buttonLabel = "更新する",
                onRegister = { toDoText ->
                    viewModel.updateToDo(toDoText)
                    navController.popBackStack()
                },
                onCancel = cancel
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReminderRegister(
    modifier: Modifier = Modifier,
    initialToDoText: String = "",
    buttonLabel: String = "",
    inputEnabled: Boolean = true,
    onRegister: (String) -> Unit = {},
    onCancel: () -> Unit
) {
    var toDoText: TextFieldValue by remember {
        mutableStateOf(
            TextFieldValue(
                text = initialToDoText,
                selection = TextRange(initialToDoText.length)
            )
        )
    }
    val focusRequester = remember { FocusRequester() }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .imePadding()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = toDoText,
            onValueChange = { newValue ->
                // singleLineやmaxLineを無視して改行が入ってしまうため対策
                if (!newValue.text.contains("\n")) {
                    toDoText = newValue
                }
            },
            placeholder = {
                Text(
                    text = "やること（例：掃除）",
                    modifier = Modifier.alpha(0.6f)
                )
            },
            modifier = modifier
                .fillMaxWidth()
                .focusRequester(focusRequester),
            enabled = inputEnabled,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    if (toDoText.text.isNotEmpty()) {
                        focusRequester.freeFocus()
                        onRegister(toDoText.text)
                    }
                }
            ),
            singleLine = true
        )

        Spacer(modifier = modifier.height(16.dp))

        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            OutlinedButton(
                onClick = { onCancel() }
            ) {
                Text(text = "キャンセル")
            }

            Button(
                onClick = { onRegister(toDoText.text) },
                enabled = inputEnabled && toDoText.text.isNotEmpty()
            ) {
                Text(text = buttonLabel)
            }
        }
    }

    DisposableEffect(Unit) {
        focusRequester.requestFocus()
        onDispose { }
    }
}

@Preview(uiMode = UI_MODE_NIGHT_MASK)
@Composable
fun PreviewReminderRegister() {
    AppTheme {
        ReminderRegister(
            initialToDoText = "テスト",
            onRegister = { },
            onCancel = { }
        )
    }
}
