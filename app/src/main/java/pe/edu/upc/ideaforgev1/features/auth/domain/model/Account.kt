package pe.edu.upc.ideaforgev1.features.auth.domain.model

/**
 * Domain model representing a registered account.
 * Returned after a successful registration.
 */
data class Account(
    val id: Long,
    val email: String,
    val role: String,
    val status: String
)
