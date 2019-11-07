package ch.hearc.fidarc.ui.data

import ch.hearc.fidarc.ui.data.model.LoggedInUser
import ch.hearc.fidarc.ui.network.FidarcAPI
import java.io.IOException

import android.util.Log
import ch.hearc.fidarc.ui.network.FidarcAPIService

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {

    suspend fun login(username: String, password: String): Result<LoggedInUser> {
        try {
            val fakeUser = LoggedInUser(java.util.UUID.randomUUID().toString(), "Jane Doe", "company", "test")
            return Result.Success(fakeUser)
        } catch (e: Throwable) {
            return Result.Error(IOException("Error logging in", e))
        }
    }

    fun logout() {
        // TODO: revoke authentication
    }
}

