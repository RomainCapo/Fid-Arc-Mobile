package ch.hearc.fidarc.ui.login.data.model

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
data class LoggedInUser(
    val userId: String,
    val displayName: String,
    val role: String,
    val token: String
)
