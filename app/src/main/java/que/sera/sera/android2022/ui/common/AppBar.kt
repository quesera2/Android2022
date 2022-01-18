package que.sera.sera.android2022.ui.common

import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import que.sera.sera.android2022.R

@Preview
@Composable
fun AppBar(
    modifier: Modifier = Modifier
) = SmallTopAppBar(
    modifier = modifier,
    title = { Text(stringResource(id = R.string.app_name)) }
)