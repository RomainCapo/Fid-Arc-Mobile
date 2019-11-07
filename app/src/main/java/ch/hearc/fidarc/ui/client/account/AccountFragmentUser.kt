package ch.hearc.fidarc.ui.client.account

import android.os.Bundle
import android.provider.Contacts
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ch.hearc.fidarc.R
import ch.hearc.fidarc.ui.network.FidarcAPI
import ch.hearc.fidarc.ui.network.FidarcAPIService
import kotlinx.android.synthetic.main.fragment_account_user.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import java.net.URL
import java.util.concurrent.Executors

class AccountFragmentUser : Fragment() {
    private var fidelityCardList = ArrayList<FidelityCard>()
    private var recyclerView: RecyclerView? = null
    private var mAdapter: FidelityCardAdapter? = null
    private var client: FidarcAPIService = FidarcAPI.retrofitService

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var accountView = inflater.inflate(R.layout.fragment_account_user, container, false)
        recyclerView = accountView.findViewById<View>(R.id.recycler_view_fidelity_cards) as RecyclerView
        mAdapter =
            FidelityCardAdapter(
                context!!,
                fidelityCardList
            )

        val mLayoutManager = LinearLayoutManager(activity)
        recyclerView?.layoutManager = mLayoutManager
        recyclerView?.itemAnimator = DefaultItemAnimator()
        recyclerView?.adapter = mAdapter

        prepareAccountFragmentData(accountView)

        return accountView
    }

    private fun prepareAccountFragmentData(view:View) {


        GlobalScope.launch(Dispatchers.Main) {

            val fidelityCards = client.getFidelityCards(2).data
                fidelityCards.forEach {
                    fidelityCardList.add(it)
            }
            mAdapter?.notifyDataSetChanged()
        }

        /*view.text_account_first_name_user.text = jsonArray.getJSONObject(0).getString("first_name")
        view.text_account_last_name_user.text = jsonArray.getJSONObject(0).getString("last_name")
        view.text_account_email_user.text = jsonArray.getJSONObject(0).getString("email")*/
    }
}