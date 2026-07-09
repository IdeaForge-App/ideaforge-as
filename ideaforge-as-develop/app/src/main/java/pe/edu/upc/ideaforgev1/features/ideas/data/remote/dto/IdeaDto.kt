package pe.edu.upc.ideaforgev1.features.ideas.data.remote.dto

data class IdeaDto(
    val id: Long,
    val creatorProfileId: Long,
    val title: String,
    val shortDescription: String? = null,
    val description: String? = null,
    val problem: String? = null,
    val solution: String? = null,
    val category: String? = null,
    val status: String? = null,
    val stage: String? = null,
    val collaborationMode: String? = null,
    val expectedCommitment: String? = null,
    val requiredRoles: List<RequiredRoleDto> = emptyList()
)

data class RequiredRoleDto(
    val id: Long,
    val roleName: String,
    val description: String? = null,
    val quantity: Int? = null,
    val requiredExperienceLevel: String? = null
)

data class CreateIdeaRequestDto(
    val creatorProfileId: Long,
    val title: String,
    val shortDescription: String? = null,
    val description: String,
    val problem: String? = null,
    val solution: String? = null,
    val category: String? = null,
    val stage: String? = null,
    val collaborationMode: String? = null,
    val expectedCommitment: String? = null,
    val requiredRoles: List<CreateRequiredRoleRequestDto> = emptyList()
)

data class CreateRequiredRoleRequestDto(
    val roleName: String,
    val description: String? = null,
    val quantity: Int? = null,
    val requiredExperienceLevel: String? = null
)
