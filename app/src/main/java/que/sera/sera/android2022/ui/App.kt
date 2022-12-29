package que.sera.sera.android2022.ui.app

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.plusAssign
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.bottomSheet
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import que.sera.sera.core_ui.AppTheme
import que.sera.sera.todo_detail.DetailScreen
import que.sera.sera.todo_list.MainScreen

@ExperimentalMaterialNavigationApi
@ExperimentalAnimationApi
@ExperimentalMaterial3Api
@Composable
fun MyApp() {
    AppTheme {
        val systemUiController = rememberSystemUiController()
        SideEffect {
            systemUiController.setSystemBarsColor(
                color = Color.Transparent,
                darkIcons = false
            )
        }

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
                    route = "detail/{toDoId}",
                    arguments = listOf(navArgument("toDoId") {
                        type = NavType.IntType
                    })
                ) { backStackEntry ->
                    DetailScreen(
                        viewModel = hiltViewModel(),
                        navController = navController,
                        toDoId = backStackEntry.arguments?.getInt("toDoId") ?: 0
                    )
                }
            }
        }
    }
}
