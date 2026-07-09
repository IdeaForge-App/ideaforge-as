package pe.edu.upc.ideaforgev1.features.exploration.data.remote.dto

data class IdeaViewDto(
    val id: Long,
    val creatorProfileId: Long,
    val title: String,
    val shortDescription: String? = null,
    val category: String? = null,
    val status: String? = null,
    val stage: String? = null,
    val collaborationMode: String? = null
)
