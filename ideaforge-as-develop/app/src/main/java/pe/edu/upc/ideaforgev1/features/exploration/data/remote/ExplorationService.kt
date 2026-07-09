package pe.edu.upc.ideaforgev1.features.exploration.data.remote

import pe.edu.upc.ideaforgev1.features.exploration.data.remote.dto.IdeaViewDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ExplorationService {

    @GET("exploration/ideas")
    suspend fun getIdeas(
        @Query("keyword") keyword: String? = null,
        @Query("category") category: String? = null,
        @Query("status") status: String? = null,
        @Query("stage") stage: String? = null
    ): List<IdeaViewDto>

    @GET("exploration/ideas/search")
    suspend fun searchIdeas(
        @Query("keyword") keyword: String? = null,
        @Query("category") category: String? = null,
        @Query("status") status: String? = null,
        @Query("stage") stage: String? = null
    ): List<IdeaViewDto>

    @GET("exploration/ideas/recommended")
    suspend fun getRecommendedIdeas(
        @Query("profileId") profileId: Long? = null
    ): List<IdeaViewDto>
}
