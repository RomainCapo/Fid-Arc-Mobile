package ch.hearc.fidarc.ui.company.scan

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.app.Activity
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.client.android.Intents.Scan.RESULT



class BarcodeScanningActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        IntentIntegrator(this).initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)

        if (requestCode == IntentIntegrator.REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                val contents = intent!!.getStringExtra(RESULT)

                Toast.makeText(this, "contents : $contents", Toast.LENGTH_LONG).show()
            }
        }
    }
}
