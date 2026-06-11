package pe.edu.upc.ideaforgev1.features.profiles.data.remote.dto

data class ProfileDto(
    val id: Long,
    val accountId: Long,
    val firstName: String,
    val lastName: String,
    val headline: String? = null,
    val bio: String? = null,
    val avatarUrl: String? = null,
    val experienceLevel: String? = null,
    val skills: List<SkillDto> = emptyList(),
    val interests: List<InterestDto> = emptyList()
)

data class SkillDto(
    val id: Long,
    val name: String,
    val proficiencyLevel: String? = null
)

data class InterestDto(
    val id: Long,
    val name: String
)

data class CreateProfileRequestDto(
    val accountId: Long,
    val firstName: String,
    val lastName: String,
    val headline: String? = null,
    val bio: String? = null,
    val avatarUrl: String? = null,
    val experienceLevel: String? = null,
    val skills: List<String> = emptyList(),
    val interests: List<String> = emptyList()
)
