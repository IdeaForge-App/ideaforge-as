package pe.edu.upc.ideaforgev1.core.data.local

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import pe.edu.upc.ideaforgev1.features.auth.domain.repository.SessionStore

/**
 * Secure implementation of [SessionStore] backed by [EncryptedSharedPreferences].
 *
 * All sensitive session data (token, account ID, email, role) is persisted
 * with AES-256 GCM encryption. Data survives process death and app restarts.
 *
 * Usage:
 * ```
 * val sessionManager = SessionManager(applicationContext)
 * ```
 *
 * @param context Application context — never hold Activity context to avoid leaks.
 */
class SessionManager(context: Context) : SessionStore {

    private val prefs: SharedPreferences by lazy {
        val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
        EncryptedSharedPreferences.create(
            PREFS_FILE_NAME,
            masterKeyAlias,
            context.applicationContext,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    // ─────────────────────────────────────────────
    // Read accessors
    // ─────────────────────────────────────────────

    override val accountId: Long?
        get() {
            val id = prefs.getLong(KEY_ACCOUNT_ID, INVALID_ID)
            return if (id == INVALID_ID) null else id
        }

    override val profileId: Long?
        get() {
            val id = prefs.getLong(KEY_PROFILE_ID, INVALID_ID)
            return if (id == INVALID_ID) null else id
        }

    override val email: String?
        get() = prefs.getString(KEY_EMAIL, null)

    override val role: String?
        get() = prefs.getString(KEY_ROLE, null)

    override val token: String?
        get() = prefs.getString(KEY_TOKEN, null)

    // ─────────────────────────────────────────────
    // Write operations
    // ─────────────────────────────────────────────

    override fun saveLogin(
        accountId: Long,
        email: String,
        role: String,
        token: String
    ) {
        prefs.edit()
            .putLong(KEY_ACCOUNT_ID, accountId)
            .putString(KEY_EMAIL, email)
            .putString(KEY_ROLE, role)
            .putString(KEY_TOKEN, token)
            .apply()
    }

    override fun saveProfileId(profileId: Long) {
        prefs.edit()
            .putLong(KEY_PROFILE_ID, profileId)
            .apply()
    }

    override fun clear() {
        prefs.edit().clear().apply()
    }

    override fun isLoggedIn(): Boolean {
        return accountId != null && !token.isNullOrBlank()
    }

    override fun hasProfile(): Boolean {
        return profileId != null
    }

    companion object {
        private const val PREFS_FILE_NAME = "ideaforge_secure_session"
        private const val KEY_ACCOUNT_ID = "account_id"
        private const val KEY_PROFILE_ID = "profile_id"
        private const val KEY_EMAIL = "email"
        private const val KEY_ROLE = "role"
        private const val KEY_TOKEN = "token"
        private const val INVALID_ID = -1L
    }
}
