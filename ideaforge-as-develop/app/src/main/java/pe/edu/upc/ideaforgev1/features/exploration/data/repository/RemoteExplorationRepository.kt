package pe.edu.upc.ideaforgev1.features.exploration.data.repository

import pe.edu.upc.ideaforgev1.core.data.remote.RetrofitClient
import pe.edu.upc.ideaforgev1.features.exploration.data.mapper.IdeaViewMapper
import pe.edu.upc.ideaforgev1.features.exploration.data.remote.ExplorationService
import pe.edu.upc.ideaforgev1.features.exploration.domain.model.IdeaCard
import pe.edu.upc.ideaforgev1.features.exploration.domain.repository.ExplorationRepository

class RemoteExplorationRepository(
    private val service: ExplorationService = RetrofitClient.create()
) : ExplorationRepository {

    override suspend fun getIdeas(): List<IdeaCard> {
        return service.getIdeas()
            .map { dto -> IdeaViewMapper.toDomain(dto) }
    }

    override suspend fun searchIdeas(query: String): List<IdeaCard> {
        return service.searchIdeas(keyword = query.ifBlank { null })
            .map { dto -> IdeaViewMapper.toDomain(dto) }
    }
}
