package que.sera.sera.android2022.ui.app

import androidx.compose.animation.*
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.android.material.composethemeadapter3.Mdc3Theme
import que.sera.sera.android2022.ui.detail.DetailScreen
import que.sera.sera.android2022.ui.main.MainScreen
import kotlin.math.roundToInt

@ExperimentalAnimationApi
@ExperimentalMaterial3Api
@Composable
fun MyApp() {
    Mdc3Theme {
        val navController = rememberAnimatedNavController()
        val screenHeight = LocalConfiguration.current.screenHeightDp.dp
        AnimatedNavHost(
            navController = navController,
            startDestination = "main"
        ) {
            composable(
                route = "main"
            ) {
                MainScreen(
                    viewModel = hiltViewModel(),
                    navController = navController
                )
            }
            composable(
                route = "detail",
                enterTransition = { slideInVertically(initialOffsetY = { screenHeight.value.roundToInt() }) },
                exitTransition = { slideOutVertically(targetOffsetY = { screenHeight.div(2).value.roundToInt() }) + fadeOut() }
            ) {
                DetailScreen(
                    navController = navController
                )
            }
        }
    }
}