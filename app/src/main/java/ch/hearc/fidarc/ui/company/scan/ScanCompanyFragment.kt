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
        val myIntent = Intent(activity, BarcodeScanningActivity::class.java)

        val root = inflater.inflate(R.layout.fragment_scan_company, container, false)

        val button: TextView = root.findViewById(R.id.buttonCamera)
        button.setOnClickListener {
            activity!!.startActivity(myIntent)
        }

        return root
    }
}
