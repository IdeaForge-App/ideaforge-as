package pe.edu.upc.ideaforgev1.features.auth.data.remote.dto

import pe.edu.upc.ideaforgev1.features.auth.domain.model.Account
import pe.edu.upc.ideaforgev1.features.auth.domain.model.Session

// ─────────────────────────────────────────────
// Request DTOs
// ─────────────────────────────────────────────

data class RegisterRequestDto(
    val email: String,
    val password: String
)

data class LoginRequestDto(
    val email: String,
    val password: String
)

// ─────────────────────────────────────────────
// Response DTOs
// ─────────────────────────────────────────────

data class AccountDto(
    val id: Long,
    val email: String,
    val role: String,
    val status: String
) {
    fun toDomain(): Account = Account(
        id = id,
        email = email,
        role = role,
        status = status
    )
}

data class LoginResponseDto(
    val accountId: Long,
    val email: String,
    val role: String,
    val token: String? = null
) {
    /**
     * Maps to a domain [Session].
     *
     * If the backend does not yet emit JWTs, [token] will be null and
     * we generate a placeholder. The [AuthInterceptor] will simply skip
     * attaching the header when the stored token is empty.
     */
    fun toDomain(): Session = Session(
        accountId = accountId,
        email = email,
        role = role,
        token = token ?: ""
    )
}
