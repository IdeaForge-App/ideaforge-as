package pe.edu.upc.ideaforgev1.features.ideas.presentation

data class IdeaUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null
)
