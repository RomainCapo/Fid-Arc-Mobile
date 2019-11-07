package ch.hearc.fidarc.ui.login

/**
 * User details post authentication that is exposed to the UI
 */
data class LoggedInUserView(
    val displayName: String,
    val displayLastname : String
)
