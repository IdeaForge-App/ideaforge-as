package pe.edu.upc.ideaforgev1.features.auth.domain.repository

/**
 * Abstraction for secure session persistence.
 *
 * Implementations must guarantee that sensitive data (tokens, credentials)
 * are stored using platform-level encryption (e.g., EncryptedSharedPreferences).
 *
 * This interface lives in the domain layer so that neither the ViewModel
 * nor the repository depend on concrete storage details.
 */
interface SessionStore {

    val accountId: Long?
    val profileId: Long?
    val email: String?
    val role: String?
    val token: String?

    fun saveLogin(
        accountId: Long,
        email: String,
        role: String,
        token: String
    )

    fun saveProfileId(profileId: Long)

    fun clear()

    fun isLoggedIn(): Boolean

    fun hasProfile(): Boolean
}
