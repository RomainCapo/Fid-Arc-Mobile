package ch.hearc.fidarc.ui.company.scan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.content.Intent

class ScanCompanyFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val myIntent = Intent(activity, BarcodeScanningActivity::class.java)
        activity!!.startActivity(myIntent)
    }


}
