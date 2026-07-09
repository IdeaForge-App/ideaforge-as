package pe.edu.upc.ideaforgev1.features.exploration.presentation

import pe.edu.upc.ideaforgev1.features.exploration.domain.model.IdeaCard

data class ExplorationUiState(
    val ideas: List<IdeaCard> = emptyList(),
    val query: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
