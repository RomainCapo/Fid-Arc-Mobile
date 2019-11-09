package ch.hearc.fidarc.ui.client.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ch.hearc.fidarc.R
import ch.hearc.fidarc.ui.data.model.FidelityCard
import ch.hearc.fidarc.ui.network.FidarcAPI
import ch.hearc.fidarc.ui.network.FidarcAPIService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

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

        loadClientData(accountView)

        return accountView
    }

    /**
     * Load the fidelity card on the recycler view and add user info in view
     *
     * @param view current view
     */
    private fun loadClientData(view:View) {

        GlobalScope.launch(Dispatchers.Main) {

            val fidelityCards = client.getFidelityCards().data // Read the data from the API
                fidelityCards.forEach {
                    fidelityCardList.add(it)
            }
            mAdapter?.notifyDataSetChanged()//update the adapter data
        }

        /*view.text_account_first_name_user.text = jsonArray.getJSONObject(0).getString("first_name")
        view.text_account_last_name_user.text = jsonArray.getJSONObject(0).getString("last_name")
        view.text_account_email_user.text = jsonArray.getJSONObject(0).getString("email")*/
    }
}