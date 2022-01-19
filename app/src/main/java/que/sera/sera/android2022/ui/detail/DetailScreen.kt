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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.android.material.composethemeadapter3.Mdc3Theme
import kotlinx.coroutines.delay
import que.sera.sera.android2022.model.todo.ToDo

@ExperimentalMaterial3Api
@Composable
fun DetailScreen(
    navController: NavController,
) {
    ReminderRegister(
        todo = ToDo(),
        onRegister = { _ ->

            navController.popBackStack()
        }
    )
}

@Composable
fun ReminderRegister(
    modifier: Modifier = Modifier,
    todo: ToDo,
    onRegister: (ToDo) -> Unit
) {
    var changedTodo by remember { mutableStateOf(todo) }
    val focusRequester = remember { FocusRequester() }

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
            value = changedTodo.name,
            onValueChange = { changedTodo = changedTodo.copy(name = it) },
            modifier = modifier
                .fillMaxWidth()
                .focusRequester(focusRequester),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { onRegister(changedTodo) }
            )
        )

        Spacer(modifier = modifier.height(16.dp))

        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            OutlinedButton(onClick = { /*TODO*/ }) {
                Text(text = "キャンセル")
            }

            Button(onClick = { onRegister(changedTodo) }) {
                Text(text = "登録する")
            }
        }
    }

    LaunchedEffect(Unit) {
        // BottomSheetのリサイズが終わるまで
        // フォーカス移動を遅延させる必要がある
        // TODO:スマートな方法が求められる
        delay(20)
        focusRequester.requestFocus()
    }
}

@Preview
@Composable
fun PreviewReminderRegister() {
    Mdc3Theme {
        ReminderRegister(todo = ToDo()) { }
    }
}
