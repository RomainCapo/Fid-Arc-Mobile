package ch.hearc.fidarc.ui.account

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ch.hearc.fidarc.R
import kotlinx.android.synthetic.main.fidelity_card_list_row.view.*
import kotlinx.android.synthetic.main.fragment_account.view.*
import org.json.JSONArray
import java.net.URL
import java.util.concurrent.Executors

class AccountFragment : Fragment() {
    private var fidelityCardList = ArrayList<FidelityCard>()
    private var recyclerView: RecyclerView? = null
    private var mAdapter: FidelityCardAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var accountView = inflater.inflate(R.layout.fragment_account, container, false)
        recyclerView = accountView.findViewById<View>(R.id.recycler_view_fidelity_cards) as RecyclerView
        mAdapter = FidelityCardAdapter(context!!, fidelityCardList)

        val mLayoutManager = LinearLayoutManager(activity)
        recyclerView?.layoutManager = mLayoutManager
        recyclerView?.itemAnimator = DefaultItemAnimator()
        recyclerView?.adapter = mAdapter

        prepareAccountFragmentData(accountView)

        return accountView
    }


    private fun prepareAccountFragmentData(view:View) {
        var fidelityCard = FidelityCard("McDo", 3, 10)
        fidelityCardList.add(fidelityCard)

        fidelityCard = FidelityCard("KingFood", 5, 12)
        fidelityCardList.add(fidelityCard)

        fidelityCard = FidelityCard("McDo2", 4, 5)
        fidelityCardList.add(fidelityCard)

        fidelityCard = FidelityCard("McDo3", 5, 12)
        fidelityCardList.add(fidelityCard)


        val url = "http://10.0.2.2:8000/testApiMobile/userAccount.php"

        Executors.newSingleThreadExecutor().execute {

            var jsonArray = JSONArray(URL(url).readText())


            view.text_account_first_name.text = jsonArray.getJSONObject(0).getString("first_name")
            view.text_account_last_name.text = jsonArray.getJSONObject(0).getString("last_name")
            view.text_account_email.text = jsonArray.getJSONObject(0).getString("email")
        }
    }
}