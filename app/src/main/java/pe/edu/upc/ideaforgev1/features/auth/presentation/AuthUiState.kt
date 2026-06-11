package pe.edu.upc.ideaforgev1.features.auth.presentation

data class AuthUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
