package ch.hearc.fidarc.ui.company.scan

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.app.Activity
import android.content.Context
import android.util.Log
import ch.hearc.fidarc.ui.network.FidarcAPI
import ch.hearc.fidarc.ui.network.FidarcAPIService
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.client.android.Intents.Scan.RESULT
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class BarcodeScanningActivity : AppCompatActivity() {

    private var client: FidarcAPIService = FidarcAPI.retrofitService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        IntentIntegrator(this).setDesiredBarcodeFormats(IntentIntegrator.QR_CODE).initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)

        if (requestCode == IntentIntegrator.REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                val contents = intent!!.getStringExtra(RESULT)

                Toast.makeText(this, "The QRCode has been scanned", Toast.LENGTH_LONG).show()

                val token = this!!.getSharedPreferences("user", Context.MODE_PRIVATE)
                    .getString("token", "-1")

                Log.d("token_value", token.toString())

                GlobalScope.launch(Dispatchers.Main) {
                    val response = client.addFidelityPoint("Bearer " + token!!, contents.toInt())
                    if (response.isSuccessful) {
                        Toast.makeText(
                            this@BarcodeScanningActivity,
                            "The number of has been incremented",
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        Toast.makeText(
                            this@BarcodeScanningActivity,
                            "The reward must be claimed",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }

            finish()
        }
    }
}