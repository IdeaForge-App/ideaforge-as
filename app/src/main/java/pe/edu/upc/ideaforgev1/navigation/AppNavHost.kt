package pe.edu.upc.ideaforgev1.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import pe.edu.upc.ideaforgev1.features.auth.domain.repository.SessionStore
import pe.edu.upc.ideaforgev1.features.auth.presentation.AuthViewModelFactory
import pe.edu.upc.ideaforgev1.features.auth.presentation.LoginScreen
import pe.edu.upc.ideaforgev1.features.auth.presentation.RegisterScreen
import pe.edu.upc.ideaforgev1.features.profiles.presentation.CompleteProfileScreen

/**
 * Root navigation host.
 *
 * @param sessionStore The single [SessionStore] instance owned by [IdeaForgeApplication].
 */
@Composable
fun AppNavHost(sessionStore: SessionStore) {
    val navController = rememberNavController()

    val startDestination = remember {
        if (sessionStore.isLoggedIn()) Routes.MAIN else Routes.LOGIN
    }

    val authViewModelFactory = remember { AuthViewModelFactory(sessionStore) }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Routes.LOGIN) {
            LoginScreen(
                onNavigateToRegister = {
                    navController.navigate(Routes.REGISTER)
                },
                onLoginSuccess = {
                    navController.navigate(Routes.MAIN) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                },
                viewModel = viewModel(factory = authViewModelFactory)
            )
        }

        composable(Routes.REGISTER) {
            RegisterScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onRegisterSuccess = {
                    navController.navigate(Routes.COMPLETE_PROFILE)
                },
                viewModel = viewModel(factory = authViewModelFactory)
            )
        }

        composable(Routes.COMPLETE_PROFILE) {
            CompleteProfileScreen(
                onProfileCompleted = {
                    navController.navigate(Routes.MAIN) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.MAIN) {
            MainScreen()
        }
    }
}
