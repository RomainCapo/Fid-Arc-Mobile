package ch.hearc.fidarc.ui.company.scan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.content.Intent
import ch.hearc.fidarc.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView


class ScanCompanyFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)

        // Create an intent of BarcodeScanning activity
        val myIntent = Intent(activity, BarcodeScanningActivity::class.java)

        // declare a root var for accessed the content of the layout
        val root = inflater.inflate(R.layout.fragment_scan_company, container, false)

        // get the button on the layout and add le listener on it
        val button: TextView = root.findViewById(R.id.buttonCamera)
        button.setOnClickListener {
            // start activity when button is clicked
            activity!!.startActivity(myIntent)
        }

        return root
    }
}
