package ch.hearc.fidarc.ui.data.model

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
data class User(
    val id: Int,
    val name: String,
    val lastname: String,
    val email: String,
    val role_names: List<String>,
    val company: Company? = null
)
