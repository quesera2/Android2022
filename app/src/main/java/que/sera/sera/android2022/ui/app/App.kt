package que.sera.sera.android2022.ui.app

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.material.composethemeadapter3.Mdc3Theme
import que.sera.sera.android2022.ui.detail.DetailScreen
import que.sera.sera.android2022.ui.main.MainScreen

@ExperimentalMaterial3Api
@Composable
fun MyApp() {
    Mdc3Theme {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = "main") {
            composable("main") {
                MainScreen(
                    viewModel = hiltViewModel(),
                    navController = navController
                )
            }
            composable("detail") {
                DetailScreen(
                    navController = navController
                )
            }
        }
    }
}