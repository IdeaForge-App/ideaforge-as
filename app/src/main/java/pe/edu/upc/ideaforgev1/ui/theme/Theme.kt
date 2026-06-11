package pe.edu.upc.ideaforgev1.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val IdeaForgeDarkColorScheme = darkColorScheme(
    primary = PurplePrimary,
    secondary = CyanSecondary,
    tertiary = MagentaAccent,
    background = NightBackground,
    surface = DarkSurface,
    surfaceVariant = DarkSurfaceVariant,
    onPrimary = SoftWhite,
    onSecondary = SoftWhite,
    onTertiary = SoftWhite,
    onBackground = SoftWhite,
    onSurface = SoftWhite,
    onSurfaceVariant = TextSecondary,
    error = ErrorCoral
)

@Composable
fun IdeaForgev1Theme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = IdeaForgeDarkColorScheme,
        typography = Typography,
        content = content
    )
}
