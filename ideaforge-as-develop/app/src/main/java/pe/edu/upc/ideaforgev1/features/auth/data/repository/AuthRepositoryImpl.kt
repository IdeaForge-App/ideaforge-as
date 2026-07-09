package pe.edu.upc.ideaforgev1.features.auth.data.repository

import pe.edu.upc.ideaforgev1.core.data.remote.RetrofitClient
import pe.edu.upc.ideaforgev1.features.auth.data.remote.AuthService
import pe.edu.upc.ideaforgev1.features.auth.data.remote.dto.AccountDto
import pe.edu.upc.ideaforgev1.features.auth.data.remote.dto.LoginRequestDto
import pe.edu.upc.ideaforgev1.features.auth.data.remote.dto.LoginResponseDto
import pe.edu.upc.ideaforgev1.features.auth.data.remote.dto.RegisterRequestDto
import pe.edu.upc.ideaforgev1.features.auth.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val service: AuthService = RetrofitClient.create()
) : AuthRepository {

    override suspend fun login(
        email: String,
        password: String
    ): LoginResponseDto {
        return service.login(
            LoginRequestDto(
                email = email,
                password = password
            )
        )
    }

    override suspend fun register(
        email: String,
        password: String
    ): AccountDto {
        return service.register(
            RegisterRequestDto(
                email = email,
                password = password
            )
        )
    }
}
