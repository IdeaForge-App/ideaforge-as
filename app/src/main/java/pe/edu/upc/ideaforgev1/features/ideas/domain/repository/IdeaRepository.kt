package pe.edu.upc.ideaforgev1.features.ideas.domain.repository

import pe.edu.upc.ideaforgev1.features.ideas.data.remote.dto.CreateIdeaRequestDto
import pe.edu.upc.ideaforgev1.features.ideas.data.remote.dto.IdeaDto

interface IdeaRepository {
    suspend fun createIdea(request: CreateIdeaRequestDto): IdeaDto
    suspend fun getIdeaById(ideaId: Long): IdeaDto
}
