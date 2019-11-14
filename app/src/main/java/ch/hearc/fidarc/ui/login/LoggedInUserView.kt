package ch.hearc.fidarc.ui.login

import ch.hearc.fidarc.ui.data.model.Token
import ch.hearc.fidarc.ui.data.model.User

/**
 * User details post authentication that is exposed to the UI
 */
data class LoggedInUserView(
    val user: User,
    val token: Token,
    val displayName: String,
    val displayLastname : String
)
