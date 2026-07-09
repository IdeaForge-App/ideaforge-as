package pe.edu.upc.ideaforgev1.core.data.remote

import okhttp3.OkHttpClient
import pe.edu.upc.ideaforgev1.features.auth.domain.repository.SessionStore
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Centralized Retrofit client factory.
 *
 * Must be initialized once at app startup via [init] before any
 * network calls are made. This provides the [SessionStore]-aware
 * [AuthInterceptor] so all authenticated requests carry the JWT.
 *
 * Usage in [MainActivity] or Application class:
 * ```
 * RetrofitClient.init(sessionManager)
 * ```
 */
object RetrofitClient {

    private const val CONNECT_TIMEOUT_SECONDS = 15L
    private const val READ_TIMEOUT_SECONDS = 30L
    private const val WRITE_TIMEOUT_SECONDS = 30L

    private lateinit var retrofit: Retrofit

    /**
     * Initializes the Retrofit instance with the given [sessionStore].
     * Must be called once before [create] is used.
     * Subsequent calls are silently ignored (idempotent).
     */
    fun init(sessionStore: SessionStore) {
        if (::retrofit.isInitialized) return

        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(CONNECT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .addInterceptor(AuthInterceptor(sessionStore))
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    /**
     * Creates a Retrofit service implementation for the given interface [T].
     *
     * @throws UninitializedPropertyAccessException if [init] has not been called.
     */
    inline fun <reified T> create(): T {
        check(::retrofit.isInitialized) {
            "RetrofitClient.init(sessionStore) must be called before create()"
        }
        return retrofit.create(T::class.java)
    }
}
