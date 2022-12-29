package que.sera.sera.core_ui

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.LayoutDirection
import com.google.accompanist.themeadapter.material3.createMdc3Theme

@Composable
fun AppTheme(
    content: @Composable () -> Unit
) {
    val isDarkMode = isSystemInDarkTheme()
    val dynamicColor = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
    val colorScheme = when {
        dynamicColor && isDarkMode -> dynamicDarkColorScheme(LocalContext.current)
        dynamicColor && !isDarkMode -> dynamicLightColorScheme(LocalContext.current)
        else -> {
            val fallbackTheme = createMdc3Theme(
                context = LocalContext.current,
                layoutDirection = LayoutDirection.Ltr
            )
            fallbackTheme.colorScheme
        }
    }
    MaterialTheme(
        colorScheme = requireNotNull(colorScheme),
        content = content
    )
}