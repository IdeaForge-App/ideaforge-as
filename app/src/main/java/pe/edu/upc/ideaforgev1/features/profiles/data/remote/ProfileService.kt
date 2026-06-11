package pe.edu.upc.ideaforgev1.features.profiles.data.remote

import pe.edu.upc.ideaforgev1.features.profiles.data.remote.dto.ProfileDto
import retrofit2.http.GET
import retrofit2.http.Path

interface ProfileService {

    @GET("profiles/by-account/{accountId}")
    suspend fun getProfileByAccountId(
        @Path("accountId") accountId: Long
    ): ProfileDto
}
