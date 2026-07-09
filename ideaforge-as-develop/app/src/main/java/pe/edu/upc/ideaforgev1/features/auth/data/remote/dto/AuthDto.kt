package pe.edu.upc.ideaforgev1.features.auth.data.remote.dto

data class RegisterRequestDto(
    val email: String,
    val password: String
)

data class LoginRequestDto(
    val email: String,
    val password: String
)

data class AccountDto(
    val id: Long,
    val email: String,
    val role: String,
    val status: String
)

data class LoginResponseDto(
    val accountId: Long,
    val email: String,
    val role: String,
    val token: String? = null
)
