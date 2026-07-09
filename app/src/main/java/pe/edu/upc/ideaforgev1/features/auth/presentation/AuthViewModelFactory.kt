package pe.edu.upc.ideaforgev1.features.auth.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pe.edu.upc.ideaforgev1.core.data.remote.RetrofitClient
import pe.edu.upc.ideaforgev1.features.auth.data.remote.AuthService
import pe.edu.upc.ideaforgev1.features.auth.data.repository.AuthRepositoryImpl
import pe.edu.upc.ideaforgev1.features.auth.domain.repository.AuthRepository
import pe.edu.upc.ideaforgev1.features.auth.domain.repository.SessionStore

/**
 * Factory for creating [AuthViewModel] with its required dependencies.
 *
 * This bridges the manual DI approach with Compose's `viewModel()` call.
 * If/when Hilt is adopted, this factory can be removed in favor of
 * `@HiltViewModel` + `@Inject constructor`.
 */
class AuthViewModelFactory(
    private val sessionStore: SessionStore
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            val authService: AuthService = RetrofitClient.create()
            val authRepository: AuthRepository = AuthRepositoryImpl(authService)
            return AuthViewModel(authRepository, sessionStore) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
