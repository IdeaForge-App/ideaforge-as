package pe.edu.upc.ideaforgev1.features.auth.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import pe.edu.upc.ideaforgev1.features.auth.domain.repository.AuthRepository
import pe.edu.upc.ideaforgev1.features.auth.domain.repository.SessionStore
import retrofit2.HttpException
import java.io.IOException

/**
 * ViewModel for Login and Register screens.
 *
 * Responsibilities:
 * - Input validation (email format, password complexity).
 * - Client-side brute-force throttling after [MAX_FAILED_ATTEMPTS].
 * - Delegating auth operations to [AuthRepository].
 * - Persisting session via [SessionStore].
 * - Exposing a single, immutable [AuthUiState] for the UI.
 *
 * This ViewModel does NOT interact with ProfileService. Profile
 * resolution after login is the responsibility of the Profiles
 * bounded context (e.g., a SessionBootstrapViewModel).
 */
class AuthViewModel(
    private val authRepository: AuthRepository,
    private val sessionStore: SessionStore
) : ViewModel() {

    var uiState by mutableStateOf(AuthUiState())
        private set

    private var failedLoginAttempts = 0
    private var throttleJob: Job? = null

    // ─────────────────────────────────────────────
    // Login
    // ─────────────────────────────────────────────

    fun login(
        email: String,
        password: String,
        onSuccess: () -> Unit
    ) {
        // Clear previous field errors
        uiState = uiState.copy(emailError = null, passwordError = null, errorMessage = null)

        // Check throttle
        if (uiState.isLoginThrottled) {
            uiState = uiState.copy(
                errorMessage = "Too many attempts. Wait ${uiState.throttleRemainingSeconds}s."
            )
            return
        }

        // Validate inputs
        val emailValidation = validateEmail(email)
        if (emailValidation != null) {
            uiState = uiState.copy(emailError = emailValidation)
            return
        }

        if (password.isBlank()) {
            uiState = uiState.copy(passwordError = "Password is required.")
            return
        }

        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)

            try {
                val session = authRepository.login(
                    email = email.trim(),
                    password = password
                )

                sessionStore.saveLogin(
                    accountId = session.accountId,
                    email = session.email,
                    role = session.role,
                    token = session.token
                )

                // Reset throttle on success
                failedLoginAttempts = 0

                uiState = uiState.copy(isLoading = false, errorMessage = null)
                onSuccess()
            } catch (e: HttpException) {
                handleLoginFailure("Invalid email or password.")
            } catch (e: IOException) {
                uiState = uiState.copy(
                    isLoading = false,
                    errorMessage = "Unable to connect. Please check your network."
                )
            } catch (_: Exception) {
                handleLoginFailure("Authentication failed. Please try again.")
            }
        }
    }

    // ─────────────────────────────────────────────
    // Register
    // ─────────────────────────────────────────────

    fun register(
        email: String,
        password: String,
        confirmPassword: String,
        onSuccess: () -> Unit
    ) {
        // Clear previous field errors
        uiState = uiState.copy(emailError = null, passwordError = null, errorMessage = null)

        // Validate inputs
        val emailValidation = validateEmail(email)
        if (emailValidation != null) {
            uiState = uiState.copy(emailError = emailValidation)
            return
        }

        val passwordValidation = validatePassword(password)
        if (passwordValidation != null) {
            uiState = uiState.copy(passwordError = passwordValidation)
            return
        }

        if (password != confirmPassword) {
            uiState = uiState.copy(passwordError = "Passwords do not match.")
            return
        }

        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)

            try {
                authRepository.register(
                    email = email.trim(),
                    password = password
                )

                uiState = uiState.copy(isLoading = false, errorMessage = null)
                onSuccess()
            } catch (e: HttpException) {
                uiState = uiState.copy(
                    isLoading = false,
                    errorMessage = "This email may already be registered."
                )
            } catch (e: IOException) {
                uiState = uiState.copy(
                    isLoading = false,
                    errorMessage = "Unable to connect. Please check your network."
                )
            } catch (_: Exception) {
                uiState = uiState.copy(
                    isLoading = false,
                    errorMessage = "Registration failed. Please try again."
                )
            }
        }
    }

    // ─────────────────────────────────────────────
    // Password Strength (for real-time UI feedback)
    // ─────────────────────────────────────────────

    fun onPasswordChanged(password: String) {
        uiState = uiState.copy(
            passwordStrength = calculatePasswordStrength(password)
        )
    }

    // ─────────────────────────────────────────────
    // Validation helpers
    // ─────────────────────────────────────────────

    private fun validateEmail(email: String): String? {
        if (email.isBlank()) return "Email is required."
        if (!EMAIL_PATTERN.matches(email.trim())) return "Enter a valid email address."
        return null
    }

    private fun validatePassword(password: String): String? {
        if (password.isBlank()) return "Password is required."
        if (password.length < MIN_PASSWORD_LENGTH) {
            return "Password must be at least $MIN_PASSWORD_LENGTH characters."
        }
        if (!password.any { it.isUpperCase() }) {
            return "Password must contain at least one uppercase letter."
        }
        if (!password.any { it.isDigit() }) {
            return "Password must contain at least one number."
        }
        return null
    }

    private fun calculatePasswordStrength(password: String): Int {
        if (password.isBlank()) return 0
        var score = 0
        if (password.length >= MIN_PASSWORD_LENGTH) score += 25
        if (password.length >= 12) score += 15
        if (password.any { it.isUpperCase() }) score += 20
        if (password.any { it.isLowerCase() }) score += 10
        if (password.any { it.isDigit() }) score += 15
        if (password.any { !it.isLetterOrDigit() }) score += 15
        return score.coerceAtMost(100)
    }

    // ─────────────────────────────────────────────
    // Brute-force throttling
    // ─────────────────────────────────────────────

    private fun handleLoginFailure(message: String) {
        failedLoginAttempts++

        if (failedLoginAttempts >= MAX_FAILED_ATTEMPTS) {
            startThrottleCountdown()
            uiState = uiState.copy(
                isLoading = false,
                errorMessage = "Too many failed attempts. Please wait.",
                isLoginThrottled = true
            )
        } else {
            uiState = uiState.copy(
                isLoading = false,
                errorMessage = message
            )
        }
    }

    private fun startThrottleCountdown() {
        throttleJob?.cancel()
        throttleJob = viewModelScope.launch {
            for (remaining in THROTTLE_DURATION_SECONDS downTo 1) {
                uiState = uiState.copy(
                    isLoginThrottled = true,
                    throttleRemainingSeconds = remaining
                )
                delay(1_000L)
            }
            uiState = uiState.copy(
                isLoginThrottled = false,
                throttleRemainingSeconds = 0,
                errorMessage = null
            )
            failedLoginAttempts = 0
        }
    }

    companion object {
        private const val MIN_PASSWORD_LENGTH = 8
        private const val MAX_FAILED_ATTEMPTS = 5
        private const val THROTTLE_DURATION_SECONDS = 30

        private val EMAIL_PATTERN = Regex(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
        )
    }
}
