package pe.edu.upc.ideaforgev1.navigation

data class MainTab(
    val route: String,
    val label: String,
    val icon: String
)

val mainTabs = listOf(
    MainTab(
        route = Routes.HOME,
        label = "Home",
        icon = "⌂"
    ),
    MainTab(
        route = Routes.SEARCH,
        label = "Search",
        icon = "⌕"
    ),
    MainTab(
        route = Routes.CREATE_IDEA,
        label = "Create",
        icon = "+"
    ),
    MainTab(
        route = Routes.MESSAGES,
        label = "Messages",
        icon = "✉"
    ),
    MainTab(
        route = Routes.PROFILE,
        label = "Profile",
        icon = "◎"
    )
)
