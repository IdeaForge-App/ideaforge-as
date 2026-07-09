package pe.edu.upc.ideaforgev1.features.auth.domain.repository

import pe.edu.upc.ideaforgev1.features.auth.domain.model.Account
import pe.edu.upc.ideaforgev1.features.auth.domain.model.Session

/**
 * Contract for authentication operations.
 *
 * The domain layer defines this interface using only domain models
 * ([Session], [Account]). The data layer provides the implementation
 * that maps network DTOs to these models.
 */
interface AuthRepository {

    /**
     * Authenticates a user and returns a [Session] containing the JWT.
     *
     * @throws AuthenticationException on invalid credentials (HTTP 401/403).
     * @throws NetworkException on connectivity failures.
     */
    suspend fun login(email: String, password: String): Session

    /**
     * Creates a new account and returns the [Account] details.
     *
     * @throws ConflictException if the email is already registered (HTTP 409).
     * @throws NetworkException on connectivity failures.
     */
    suspend fun register(email: String, password: String): Account
}
