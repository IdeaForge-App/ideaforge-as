package pe.edu.upc.ideaforgev1.features.profiles.domain.model

data class Profile(
    val id: Long,
    val accountId: Long,
    val fullName: String,
    val headline: String,
    val bio: String
)
