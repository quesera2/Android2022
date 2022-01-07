package que.sera.sera.android2022

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import que.sera.sera.android2022.ui.main.MainScreen
import que.sera.sera.android2022.ui.main.MainViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = MainViewModel()
        setContent {
            MainScreen(viewModel)
        }
    }
}