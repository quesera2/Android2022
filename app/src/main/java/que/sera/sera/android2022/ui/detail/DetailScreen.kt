package que.sera.sera.android2022.ui.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import que.sera.sera.android2022.ui.common.AppBar

@ExperimentalMaterial3Api
@Composable
fun DetailScreen(
    navController: NavController,
) {
    Scaffold(
        topBar = { AppBar() },
        content = {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Button(onClick = {
                    //TODO 画面遷移テスト用
                    navController.popBackStack()
                }) {
                    Text(text = "戻る")
                }
            }
        }
    )
}