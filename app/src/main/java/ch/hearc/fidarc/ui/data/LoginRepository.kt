package ch.hearc.fidarc.ui.data

import android.content.Context
import ch.hearc.fidarc.ui.data.model.UserToken

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class LoginRepository(val dataSource: LoginDataSource) {

    // in-memory cache of the loggedInUser object
    var userToken: UserToken? = null
        private set

    val isLoggedIn: Boolean
        get() = userToken != null

    init {
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
        userToken = null
    }

    fun logout(context: Context) {
        userToken = null
        dataSource.logout(context)
    }

    suspend fun login(username: String, password: String): Result<UserToken> {
        // handle login
        val result = dataSource.login(username, password)

        if (result is Result.Success) {
            setLoggedInUser(result.data)
        }

        return result
    }

    private fun setLoggedInUser(loggedInUserToken: UserToken) {
        this.userToken = loggedInUserToken
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }
}
