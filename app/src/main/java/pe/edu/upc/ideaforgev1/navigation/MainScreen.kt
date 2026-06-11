package pe.edu.upc.ideaforgev1.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import pe.edu.upc.ideaforgev1.features.conversations.presentation.MessagesScreen
import pe.edu.upc.ideaforgev1.features.exploration.presentation.HomeScreen
import pe.edu.upc.ideaforgev1.features.exploration.presentation.SearchScreen
import pe.edu.upc.ideaforgev1.features.ideas.presentation.CreateIdeaScreen
import pe.edu.upc.ideaforgev1.features.ideas.presentation.IdeaDetailScreen
import pe.edu.upc.ideaforgev1.features.profiles.presentation.ProfileScreen

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface
            ) {
                mainTabs.forEach { tab ->
                    NavigationBarItem(
                        selected = currentRoute == tab.route,
                        onClick = {
                            navController.navigate(tab.route) {
                                popUpTo(Routes.HOME) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Text(tab.icon)
                        },
                        label = {
                            Text(tab.label)
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Routes.HOME,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Routes.HOME) {
                HomeScreen(
                    onIdeaClick = { ideaId ->
                        navController.navigate(Routes.ideaDetail(ideaId))
                    }
                )
            }

            composable(Routes.SEARCH) {
                SearchScreen(
                    onIdeaClick = { ideaId ->
                        navController.navigate(Routes.ideaDetail(ideaId))
                    }
                )
            }

            composable(Routes.CREATE_IDEA) {
                CreateIdeaScreen(
                    onIdeaCreated = {
                        navController.navigate(Routes.HOME) {
                            popUpTo(Routes.HOME) {
                                inclusive = true
                            }
                        }
                    }
                )
            }

            composable(Routes.MESSAGES) {
                MessagesScreen()
            }

            composable(Routes.PROFILE) {
                ProfileScreen()
            }

            composable("${Routes.IDEA_DETAIL}/{ideaId}") { backStackEntry ->
                val ideaId = backStackEntry.arguments
                    ?.getString("ideaId")
                    ?.toLongOrNull()
                    ?: 0L

                IdeaDetailScreen(
                    ideaId = ideaId,
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}
