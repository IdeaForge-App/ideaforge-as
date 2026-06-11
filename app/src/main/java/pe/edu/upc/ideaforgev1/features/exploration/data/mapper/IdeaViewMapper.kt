package pe.edu.upc.ideaforgev1.features.exploration.data.mapper

import pe.edu.upc.ideaforgev1.features.exploration.data.remote.dto.IdeaViewDto
import pe.edu.upc.ideaforgev1.features.exploration.domain.model.IdeaCard

object IdeaViewMapper {

    fun toDomain(dto: IdeaViewDto): IdeaCard {
        return IdeaCard(
            id = dto.id,
            title = dto.title,
            description = dto.shortDescription ?: "No description available.",
            category = dto.category ?: "General",
            membersCount = 1,
            roles = buildRoles(dto),
            actionLabel = "View"
        )
    }

    private fun buildRoles(dto: IdeaViewDto): List<String> {
        val roles = mutableListOf<String>()

        dto.category?.let { roles.add(it) }
        dto.stage?.let { roles.add(it) }
        dto.collaborationMode?.let { roles.add(it) }

        return if (roles.isEmpty()) listOf("Developer") else roles.take(3)
    }
}
