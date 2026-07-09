package pe.edu.upc.ideaforgev1.features.auth.data.repository

import pe.edu.upc.ideaforgev1.features.auth.data.remote.AuthService
import pe.edu.upc.ideaforgev1.features.auth.data.remote.dto.LoginRequestDto
import pe.edu.upc.ideaforgev1.features.auth.data.remote.dto.RegisterRequestDto
import pe.edu.upc.ideaforgev1.features.auth.domain.model.Account
import pe.edu.upc.ideaforgev1.features.auth.domain.model.Session
import pe.edu.upc.ideaforgev1.features.auth.domain.repository.AuthRepository

/**
 * Concrete implementation of [AuthRepository].
 *
 * Receives [AuthService] via constructor injection instead of
 * instantiating it internally, enabling testing and decoupling
 * from the Retrofit singleton.
 */
class AuthRepositoryImpl(
    private val service: AuthService
) : AuthRepository {

    override suspend fun login(
        email: String,
        password: String
    ): Session {
        val response = service.login(
            LoginRequestDto(
                email = email,
                password = password
            )
        )
        return response.toDomain()
    }

    override suspend fun register(
        email: String,
        password: String
    ): Account {
        val response = service.register(
            RegisterRequestDto(
                email = email,
                password = password
            )
        )
        return response.toDomain()
    }
}
