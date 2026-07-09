package pe.edu.upc.ideaforgev1.features.auth.presentation

/**
 * Immutable UI state for the authentication screens.
 *
 * @property isLoading        True while a network request is in progress.
 * @property errorMessage     General error message (e.g., "Invalid credentials").
 * @property emailError       Validation error specific to the email field.
 * @property passwordError    Validation error specific to the password field.
 * @property isLoginThrottled True when the user has exceeded max login attempts.
 * @property throttleRemainingSeconds Countdown seconds until the throttle lifts.
 * @property passwordStrength Visual indicator for password complexity (0–100).
 */
data class AuthUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null,
    val isLoginThrottled: Boolean = false,
    val throttleRemainingSeconds: Int = 0,
    val passwordStrength: Int = 0
)
