package ch.hearc.fidarc

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ch.hearc.fidarc.ui.login.LoginActivity

class SplashActivity : AppCompatActivity() {

    private val splashTime = 3000L
    private  lateinit var myHandler : Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Toast.makeText(this, "TEST", Toast.LENGTH_LONG)

        myHandler = Handler()

        myHandler.postDelayed({
            checkUserInfo()
            finish()
        }, splashTime)
    }

    private fun checkUserInfo() {
        // TODO check if user already logged
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

}