package pe.edu.upc.ideaforgev1.features.auth.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pe.edu.upc.ideaforgev1.core.data.local.SessionManager
import pe.edu.upc.ideaforgev1.core.data.remote.RetrofitClient
import pe.edu.upc.ideaforgev1.features.auth.data.repository.AuthRepositoryImpl
import pe.edu.upc.ideaforgev1.features.auth.domain.repository.AuthRepository
import pe.edu.upc.ideaforgev1.features.profiles.data.remote.ProfileService
import retrofit2.HttpException
import java.io.IOException

class AuthViewModel(
    private val authRepository: AuthRepository = AuthRepositoryImpl(),
    private val profileService: ProfileService = RetrofitClient.create()
) : ViewModel() {

    var uiState by mutableStateOf(AuthUiState())
        private set

    fun login(
        email: String,
        password: String,
        onSuccess: () -> Unit
    ) {
        if (email.isBlank() || password.isBlank()) {
            uiState = uiState.copy(
                errorMessage = "Please enter email and password."
            )
            return
        }

        viewModelScope.launch {
            uiState = uiState.copy(
                isLoading = true,
                errorMessage = null
            )

            try {
                val response = authRepository.login(
                    email = email.trim(),
                    password = password
                )

                SessionManager.saveLogin(
                    accountId = response.accountId,
                    email = response.email,
                    role = response.role,
                    token = response.token
                )

                try {
                    val profile = profileService.getProfileByAccountId(response.accountId)
                    SessionManager.saveProfileId(profile.id)
                } catch (_: Exception) {
                    // The account exists, but profile was not created yet.
                }

                uiState = uiState.copy(
                    isLoading = false,
                    errorMessage = null
                )

                onSuccess()
            } catch (e: HttpException) {
                uiState = uiState.copy(
                    isLoading = false,
                    errorMessage = "Invalid email or password."
                )
            } catch (e: IOException) {
                uiState = uiState.copy(
                    isLoading = false,
                    errorMessage = "Cannot connect to local backend. Check if Spring Boot is running."
                )
            } catch (e: Exception) {
                uiState = uiState.copy(
                    isLoading = false,
                    errorMessage = "Login failed. Try again."
                )
            }
        }
    }

    fun register(
        email: String,
        password: String,
        confirmPassword: String,
        onSuccess: () -> Unit
    ) {
        if (email.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
            uiState = uiState.copy(
                errorMessage = "Please complete all fields."
            )
            return
        }

        if (password != confirmPassword) {
            uiState = uiState.copy(
                errorMessage = "Passwords do not match."
            )
            return
        }

        viewModelScope.launch {
            uiState = uiState.copy(
                isLoading = true,
                errorMessage = null
            )

            try {
                authRepository.register(
                    email = email.trim(),
                    password = password
                )

                uiState = uiState.copy(
                    isLoading = false,
                    errorMessage = null
                )

                onSuccess()
            } catch (e: HttpException) {
                uiState = uiState.copy(
                    isLoading = false,
                    errorMessage = "This email may already be registered."
                )
            } catch (e: IOException) {
                uiState = uiState.copy(
                    isLoading = false,
                    errorMessage = "Cannot connect to local backend. Check if Spring Boot is running."
                )
            } catch (e: Exception) {
                uiState = uiState.copy(
                    isLoading = false,
                    errorMessage = "Registration failed. Try again."
                )
            }
        }
    }
}
