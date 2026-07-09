package pe.edu.upc.ideaforgev1.features.profiles.presentation

import androidx.compose.runtime.Composable
import pe.edu.upc.ideaforgev1.core.presentation.components.IdeaForgePlaceholderScreen

@Composable
fun CompleteProfileScreen(
    onProfileCompleted: () -> Unit = {}
) {
    IdeaForgePlaceholderScreen(
        title = "Complete Profile",
        subtitle = "Here the user will add role, skills and interests."
    )
}
