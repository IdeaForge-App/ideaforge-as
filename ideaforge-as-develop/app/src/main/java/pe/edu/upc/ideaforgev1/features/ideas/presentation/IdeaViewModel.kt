package pe.edu.upc.ideaforgev1.features.ideas.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pe.edu.upc.ideaforgev1.core.data.fake.FakeData
import pe.edu.upc.ideaforgev1.core.data.local.SessionManager
import pe.edu.upc.ideaforgev1.features.ideas.data.remote.dto.CreateIdeaRequestDto
import pe.edu.upc.ideaforgev1.features.ideas.data.remote.dto.CreateRequiredRoleRequestDto
import pe.edu.upc.ideaforgev1.features.ideas.data.repository.IdeaRepositoryImpl
import pe.edu.upc.ideaforgev1.features.ideas.domain.repository.IdeaRepository
import retrofit2.HttpException
import java.io.IOException

class IdeaViewModel(
    private val repository: IdeaRepository = IdeaRepositoryImpl()
) : ViewModel() {

    var uiState by mutableStateOf(IdeaUiState())
        private set

    fun createIdea(
        title: String,
        description: String,
        category: String,
        problem: String,
        solution: String,
        roles: List<String>,
        onSuccess: () -> Unit
    ) {
        if (!SessionManager.isLoggedIn()) {
            uiState = uiState.copy(
                errorMessage = "You must log in before creating an idea."
            )
            return
        }

        val creatorProfileId = SessionManager.profileId

        if (creatorProfileId == null) {
            uiState = uiState.copy(
                errorMessage = "Your account does not have a profile yet. Create a profile first."
            )
            return
        }

        if (title.isBlank()) {
            uiState = uiState.copy(
                errorMessage = "Please enter an idea title."
            )
            return
        }

        if (description.isBlank()) {
            uiState = uiState.copy(
                errorMessage = "Please enter a short description."
            )
            return
        }

        val finalCategory = category.ifBlank { "OTHER" }
        val finalRoles = if (roles.isEmpty()) listOf("Developer") else roles

        val request = CreateIdeaRequestDto(
            creatorProfileId = creatorProfileId,
            title = title.trim(),
            shortDescription = description.trim(),
            description = description.trim(),
            problem = problem.ifBlank { "Problem to be validated." },
            solution = solution.ifBlank { "Solution to be defined with the team." },
            category = finalCategory.uppercase(),
            stage = "IDEA",
            collaborationMode = "REMOTE",
            expectedCommitment = "MEDIUM",
            requiredRoles = finalRoles.map { role ->
                CreateRequiredRoleRequestDto(
                    roleName = role,
                    description = "Collaborator needed for $role tasks.",
                    quantity = 1,
                    requiredExperienceLevel = "JUNIOR"
                )
            }
        )

        viewModelScope.launch {
            uiState = uiState.copy(
                isLoading = true,
                errorMessage = null,
                successMessage = null
            )

            try {
                val createdIdea = repository.createIdea(request)

                FakeData.ideas.add(
                    0,
                    pe.edu.upc.ideaforgev1.features.exploration.domain.model.IdeaCard(
                        id = createdIdea.id,
                        title = createdIdea.title,
                        description = createdIdea.shortDescription ?: createdIdea.description ?: "No description available.",
                        category = createdIdea.category ?: "OTHER",
                        membersCount = 1,
                        roles = createdIdea.requiredRoles.map { it.roleName }.ifEmpty { finalRoles },
                        actionLabel = "View"
                    )
                )

                uiState = uiState.copy(
                    isLoading = false,
                    errorMessage = null,
                    successMessage = "Idea created successfully."
                )

                onSuccess()
            } catch (e: HttpException) {
                uiState = uiState.copy(
                    isLoading = false,
                    errorMessage = "Backend rejected the idea. Check profileId, category or required fields."
                )
            } catch (e: IOException) {
                uiState = uiState.copy(
                    isLoading = false,
                    errorMessage = "Cannot connect to local backend. Check if Spring Boot is running."
                )
            } catch (e: Exception) {
                uiState = uiState.copy(
                    isLoading = false,
                    errorMessage = "Could not create idea. Try again."
                )
            }
        }
    }
}
