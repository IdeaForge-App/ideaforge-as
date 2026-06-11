package pe.edu.upc.ideaforgev1.features.exploration.domain.model

data class IdeaCard(
    val id: Long,
    val title: String,
    val description: String,
    val category: String,
    val membersCount: Int,
    val roles: List<String>,
    val actionLabel: String
)
