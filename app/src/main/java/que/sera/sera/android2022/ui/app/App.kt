package que.sera.sera.android2022.ui.app

import androidx.compose.animation.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.plusAssign
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.bottomSheet
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import com.google.android.material.composethemeadapter3.Mdc3Theme
import que.sera.sera.android2022.ui.detail.DetailScreen
import que.sera.sera.android2022.ui.main.MainScreen

@ExperimentalMaterialNavigationApi
@ExperimentalAnimationApi
@ExperimentalMaterial3Api
@Composable
fun MyApp() {
    Mdc3Theme {
        val navController = rememberAnimatedNavController()
        val bottomSheetNavigator = rememberBottomSheetNavigator()
        navController.navigatorProvider += bottomSheetNavigator

        ModalBottomSheetLayout(bottomSheetNavigator) {
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
                bottomSheet(
                    route = "detail",
                ) {
                    DetailScreen(
                        navController = navController
                    )
                }
            }
        }
    }
}