package pe.edu.upc.ideaforgev1.features.ideas.data.remote

import pe.edu.upc.ideaforgev1.features.ideas.data.remote.dto.CreateIdeaRequestDto
import pe.edu.upc.ideaforgev1.features.ideas.data.remote.dto.IdeaDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface IdeaService {

    @POST("ideas")
    suspend fun createIdea(
        @Body request: CreateIdeaRequestDto
    ): IdeaDto

    @GET("ideas/{ideaId}")
    suspend fun getIdeaById(
        @Path("ideaId") ideaId: Long
    ): IdeaDto
}
