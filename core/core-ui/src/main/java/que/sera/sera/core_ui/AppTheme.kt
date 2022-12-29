package que.sera.sera.core_ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.LayoutDirection
import com.google.accompanist.themeadapter.material3.createMdc3Theme

@Composable
fun AppTheme(
    content: @Composable () -> Unit
) {
    val (colorScheme, typography, shapes) = createMdc3Theme(
        context = LocalContext.current,
        layoutDirection = LayoutDirection.Ltr
    )

    MaterialTheme(
        colorScheme = requireNotNull(colorScheme),
        shapes = requireNotNull(shapes),
        typography = requireNotNull(typography),
        content = content
    )
}