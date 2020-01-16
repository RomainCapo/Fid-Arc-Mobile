package ch.hearc.fidarc

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ch.hearc.fidarc.ui.login.LoginActivity

class SplashActivity : AppCompatActivity() {

    private val splashTime = 1000L
    private lateinit var myHandler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        myHandler = Handler()

        myHandler.postDelayed({
            checkUserInfo()
            finish()
        }, splashTime)
    }

    private fun checkUserInfo() {
        val sharedPref = this.getSharedPreferences("user", Context.MODE_PRIVATE) ?: return
        val token = sharedPref.getString("token", "-1")
        val role_names = sharedPref.getStringSet("roles", null)

        val intent: Intent
        if (token == "-1") {
            intent = Intent(this, LoginActivity::class.java)
        } else {
            if (role_names!!.contains("company")) {
                intent = Intent(this, CompanyActivity::class.java)
            } else {
                intent = Intent(this, UserActivity::class.java)
            }
        }
        startActivity(intent)
    }

}