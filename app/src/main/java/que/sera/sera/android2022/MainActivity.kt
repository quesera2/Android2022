package que.sera.sera.android2022

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.core.view.WindowCompat
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import dagger.hilt.android.AndroidEntryPoint
import que.sera.sera.android2022.ui.app.MyApp


@ExperimentalMaterialNavigationApi
@ExperimentalAnimationApi
@ExperimentalMaterial3Api
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            MyApp()
        }
    }
}
