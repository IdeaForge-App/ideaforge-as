package pe.edu.upc.ideaforgev1.core.data.local

object SessionManager {
    var accountId: Long? = null
        private set

    var profileId: Long? = null
        private set

    var email: String? = null
        private set

    var role: String? = null
        private set

    var token: String? = null
        private set

    fun saveLogin(
        accountId: Long,
        email: String,
        role: String,
        token: String?
    ) {
        this.accountId = accountId
        this.email = email
        this.role = role
        this.token = token
    }

    fun saveProfileId(profileId: Long) {
        this.profileId = profileId
    }

    fun clear() {
        accountId = null
        profileId = null
        email = null
        role = null
        token = null
    }

    fun isLoggedIn(): Boolean {
        return accountId != null
    }

    fun hasProfile(): Boolean {
        return profileId != null
    }
}
