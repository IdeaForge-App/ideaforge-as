package pe.edu.upc.ideaforgev1.features.auth.domain.model

/**
 * Domain model representing an authenticated user session.
 *
 * A valid session always has a non-null [token].
 * The [profileId] is populated lazily after login when the
 * profile bounded context resolves it.
 */
data class Session(
    val accountId: Long,
    val email: String,
    val role: String,
    val token: String
)
