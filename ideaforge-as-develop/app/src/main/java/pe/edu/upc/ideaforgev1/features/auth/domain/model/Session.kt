package pe.edu.upc.ideaforgev1.features.auth.domain.model

data class Session(
    val accountId: Long,
    val email: String,
    val role: String,
    val token: String? = null
)
