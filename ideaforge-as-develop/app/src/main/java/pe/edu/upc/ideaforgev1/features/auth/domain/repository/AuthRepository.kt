package pe.edu.upc.ideaforgev1.features.auth.domain.repository

import pe.edu.upc.ideaforgev1.features.auth.data.remote.dto.AccountDto
import pe.edu.upc.ideaforgev1.features.auth.data.remote.dto.LoginResponseDto

interface AuthRepository {
    suspend fun login(email: String, password: String): LoginResponseDto
    suspend fun register(email: String, password: String): AccountDto
}
