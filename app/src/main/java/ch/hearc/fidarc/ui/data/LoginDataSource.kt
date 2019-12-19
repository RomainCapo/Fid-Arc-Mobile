package ch.hearc.fidarc.ui.data

import android.app.ActivityManager
import android.content.Context
import android.util.Log
import ch.hearc.fidarc.BuildConfig
import ch.hearc.fidarc.ui.data.model.Token
import ch.hearc.fidarc.ui.data.model.User
import ch.hearc.fidarc.ui.data.model.UserToken
import ch.hearc.fidarc.ui.network.FidarcAPI
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {

    suspend fun login(username: String, password: String): Result<UserToken> {
        try {
            val response = FidarcAPI.retrofitService.login(username = username, password = password)
            if(response.isSuccessful) {
                val token = response.body()
                val response =
                    FidarcAPI.retrofitService.getUser(token?.token_type + " " + token?.access_token)

                if (response.isSuccessful) {
                    val user = UserToken(response.body() as User, token as Token)
                    return Result.Success(user)
                }
            }

            return Result.Error(
                IOException("Error getting details ${response.code()} ${response.message()}")
            )

        } catch (e: Throwable) {
            return Result.Error(IOException("Error logging in", e))
        }
    }

    fun logout() {
        // TODO: revoke authentication
    }
}

