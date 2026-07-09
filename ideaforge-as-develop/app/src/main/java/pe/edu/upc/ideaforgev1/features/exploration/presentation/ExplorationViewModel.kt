package pe.edu.upc.ideaforgev1.features.exploration.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pe.edu.upc.ideaforgev1.core.data.remote.ApiConstants
import pe.edu.upc.ideaforgev1.features.exploration.data.repository.FakeExplorationRepository
import pe.edu.upc.ideaforgev1.features.exploration.data.repository.RemoteExplorationRepository
import pe.edu.upc.ideaforgev1.features.exploration.domain.repository.ExplorationRepository

class ExplorationViewModel(
    private val repository: ExplorationRepository =
        if (ApiConstants.USE_REMOTE_BACKEND) RemoteExplorationRepository()
        else FakeExplorationRepository(),
    private val fallbackRepository: ExplorationRepository = FakeExplorationRepository()
) : ViewModel() {

    var uiState by mutableStateOf(ExplorationUiState())
        private set

    fun loadIdeas() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, errorMessage = null)

            try {
                val ideas = repository.getIdeas()

                uiState = uiState.copy(
                    ideas = ideas,
                    isLoading = false,
                    errorMessage = null
                )
            } catch (e: Exception) {
                val fallbackIdeas = fallbackRepository.getIdeas()

                uiState = uiState.copy(
                    ideas = fallbackIdeas,
                    isLoading = false,
                    errorMessage = "Backend unavailable. Showing fake data."
                )
            }
        }
    }

    fun searchIdeas(query: String) {
        viewModelScope.launch {
            uiState = uiState.copy(
                query = query,
                isLoading = true,
                errorMessage = null
            )

            try {
                val ideas = repository.searchIdeas(query)

                uiState = uiState.copy(
                    ideas = ideas,
                    isLoading = false,
                    errorMessage = null
                )
            } catch (e: Exception) {
                val fallbackIdeas = fallbackRepository.searchIdeas(query)

                uiState = uiState.copy(
                    ideas = fallbackIdeas,
                    isLoading = false,
                    errorMessage = "Backend unavailable. Showing fake data."
                )
            }
        }
    }

    fun applyQuickFilter(filter: String) {
        val query = if (filter == "All") "" else filter
        searchIdeas(query)
    }
}
