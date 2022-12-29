package que.sera.sera.todo_list

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.semantics

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListAppBar(
    modifier: Modifier = Modifier,
    showCompletedTask: Boolean,
    onClick: (Boolean) -> Unit
) {
    TopAppBar(
        modifier = modifier.semantics {
            val label = if (showCompletedTask) {
                "完了したタスクを表示しない"
            } else {
                "完了したタスクを表示する"
            }
            this.onClick(label = label, action = null)
        },
        title = { Text(text = "タスク一覧") },
        actions = {
            IconButton(onClick = { onClick(!showCompletedTask) }) {
                val tintColor = if (showCompletedTask) {
                    MaterialTheme.colorScheme.primary.copy(0.4f)
                } else {
                    MaterialTheme.colorScheme.primary
                }

                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_filter_list_24),
                    contentDescription = null,
                    tint = tintColor
                )
            }
        },
    )
}