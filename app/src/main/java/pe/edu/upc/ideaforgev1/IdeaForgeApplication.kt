package pe.edu.upc.ideaforgev1

import android.app.Application
import pe.edu.upc.ideaforgev1.core.data.local.SessionManager
import pe.edu.upc.ideaforgev1.core.data.remote.RetrofitClient
import pe.edu.upc.ideaforgev1.features.auth.domain.repository.SessionStore

/**
 * Application-level singleton that initializes global dependencies.
 *
 * This ensures a single [SessionManager] instance is shared across
 * the entire app lifecycle, and [RetrofitClient] is initialized
 * exactly once — even if the Activity is recreated due to
 * configuration changes.
 */
class IdeaForgeApplication : Application() {

    lateinit var sessionStore: SessionStore
        private set

    override fun onCreate() {
        super.onCreate()
        sessionStore = SessionManager(applicationContext)
        RetrofitClient.init(sessionStore)
    }
}
