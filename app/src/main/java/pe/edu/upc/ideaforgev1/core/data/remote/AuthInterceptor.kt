package pe.edu.upc.ideaforgev1.core.data.remote

import okhttp3.Interceptor
import okhttp3.Response
import pe.edu.upc.ideaforgev1.features.auth.domain.repository.SessionStore

/**
 * OkHttp [Interceptor] that attaches the JWT bearer token
 * to every outgoing HTTP request.
 *
 * If no token is available (user not logged in, or backend
 * hasn't started issuing JWTs yet), the request proceeds
 * without the Authorization header.
 *
 * This interceptor is added as an application-level interceptor
 * so it runs before caching and retries.
 */
class AuthInterceptor(
    private val sessionStore: SessionStore
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val token = sessionStore.token
        if (token.isNullOrBlank()) {
            return chain.proceed(originalRequest)
        }

        val authenticatedRequest = originalRequest.newBuilder()
            .header(HEADER_AUTHORIZATION, "$BEARER_PREFIX$token")
            .build()

        return chain.proceed(authenticatedRequest)
    }

    companion object {
        private const val HEADER_AUTHORIZATION = "Authorization"
        private const val BEARER_PREFIX = "Bearer "
    }
}
