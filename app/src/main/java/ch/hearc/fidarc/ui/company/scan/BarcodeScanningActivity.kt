package ch.hearc.fidarc.ui.company.scan

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.app.Activity
import android.app.AlertDialog
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

        lateinit var dialog: AlertDialog //Message Box object

        //If the scan is accepted
        if (requestCode == IntentIntegrator.REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                val contents = intent?.getStringExtra(RESULT)

                val token = this?.getSharedPreferences("user", Context.MODE_PRIVATE)
                    .getString("token", "-1")



                GlobalScope.launch(Dispatchers.Main) {
                    //Make a request to the API to add fidelity point

                    val response = client.addFidelityPoint("Bearer " + token!!, contents!!.toInt())

                    //If response is successful, the point is add to the user
                    if (response.isSuccessful) {
                        Toast.makeText(
                            this@BarcodeScanningActivity,
                            "The number of has been incremented",
                            Toast.LENGTH_LONG
                        ).show()
                        finish()//close activity
                    } else {
                        // Initialize a new instance of
                        val builder = AlertDialog.Builder(this@BarcodeScanningActivity)
                        builder.setTitle("User reach maximum point")
                        builder.setMessage("The user has reach the maximum point ! Please give him his reward !")
                        builder.setCancelable(false)

                        // Set a positive button and its click listener on alert dialog
                        builder.setPositiveButton("User got his reward"){dialog, which ->
                            //If the button is pressed, unlock user reward with an API request
                            GlobalScope.launch(Dispatchers.Main) {
                                client.userGotHisReward("Bearer " + token!!, contents!!.toInt())
                            }
                            dialog.dismiss()//close the message box
                            finish()//close activity
                        }

                        dialog = builder.create()// Finally, make the alert dialog using builder
                        dialog.show()// Display the alert dialog on app interface
                    }
                }
            }
        }
    }
}