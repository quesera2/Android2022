package que.sera.sera.android2022.ui.main

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource


@Composable
fun MainScreen(viewModel: MainViewModel) {
    Text(text = stringResource(viewModel.greeting))
}