package pe.edu.upc.ideaforgev1.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import pe.edu.upc.ideaforgev1.features.auth.presentation.LoginScreen
import pe.edu.upc.ideaforgev1.features.auth.presentation.RegisterScreen
import pe.edu.upc.ideaforgev1.features.profiles.presentation.CompleteProfileScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.LOGIN
    ) {
        composable(Routes.LOGIN) {
            LoginScreen(
                onNavigateToRegister = {
                    navController.navigate(Routes.REGISTER)
                },
                onLoginSuccess = {
                    navController.navigate(Routes.MAIN) {
                        popUpTo(Routes.LOGIN) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(Routes.REGISTER) {
            RegisterScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onRegisterSuccess = {
                    navController.navigate(Routes.COMPLETE_PROFILE)
                }
            )
        }

        composable(Routes.COMPLETE_PROFILE) {
            CompleteProfileScreen(
                onProfileCompleted = {
                    navController.navigate(Routes.MAIN) {
                        popUpTo(Routes.LOGIN) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(Routes.MAIN) {
            MainScreen()
        }
    }
}
