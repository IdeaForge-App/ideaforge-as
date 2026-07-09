package pe.edu.upc.ideaforgev1.features.ideas.data.repository

import pe.edu.upc.ideaforgev1.core.data.remote.RetrofitClient
import pe.edu.upc.ideaforgev1.features.ideas.data.remote.IdeaService
import pe.edu.upc.ideaforgev1.features.ideas.data.remote.dto.CreateIdeaRequestDto
import pe.edu.upc.ideaforgev1.features.ideas.data.remote.dto.IdeaDto
import pe.edu.upc.ideaforgev1.features.ideas.domain.repository.IdeaRepository

class IdeaRepositoryImpl(
    private val service: IdeaService = RetrofitClient.create()
) : IdeaRepository {

    override suspend fun createIdea(request: CreateIdeaRequestDto): IdeaDto {
        return service.createIdea(request)
    }

    override suspend fun getIdeaById(ideaId: Long): IdeaDto {
        return service.getIdeaById(ideaId)
    }
}
