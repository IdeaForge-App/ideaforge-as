package pe.edu.upc.ideaforgev1.navigation

object Routes {
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val COMPLETE_PROFILE = "complete_profile"
    const val MAIN = "main"

    const val HOME = "home"
    const val SEARCH = "search"
    const val CREATE_IDEA = "create_idea"
    const val MESSAGES = "messages"
    const val PROFILE = "profile"

    const val IDEA_DETAIL = "idea_detail"

    fun ideaDetail(ideaId: Long): String {
        return "$IDEA_DETAIL/$ideaId"
    }
}
