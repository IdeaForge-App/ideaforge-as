package pe.edu.upc.ideaforgev1.features.ideas.domain.model

data class Idea(
    val id: Long,
    val title: String,
    val shortDescription: String,
    val category: String,
    val status: String
)
