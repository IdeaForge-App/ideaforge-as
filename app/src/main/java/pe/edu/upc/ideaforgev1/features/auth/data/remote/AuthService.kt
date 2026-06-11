package pe.edu.upc.ideaforgev1.features.auth.data.remote

import pe.edu.upc.ideaforgev1.features.auth.data.remote.dto.AccountDto
import pe.edu.upc.ideaforgev1.features.auth.data.remote.dto.LoginRequestDto
import pe.edu.upc.ideaforgev1.features.auth.data.remote.dto.LoginResponseDto
import pe.edu.upc.ideaforgev1.features.auth.data.remote.dto.RegisterRequestDto
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("auth/login")
    suspend fun login(
        @Body request: LoginRequestDto
    ): LoginResponseDto

    @POST("auth/register")
    suspend fun register(
        @Body request: RegisterRequestDto
    ): AccountDto
}
