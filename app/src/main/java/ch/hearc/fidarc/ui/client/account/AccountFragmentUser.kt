package ch.hearc.fidarc.ui.client.account

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ch.hearc.fidarc.R
import ch.hearc.fidarc.ui.data.LoginDataSource
import ch.hearc.fidarc.ui.data.LoginRepository
import ch.hearc.fidarc.ui.data.model.FidelityCard
import ch.hearc.fidarc.ui.login.LoginActivity
import ch.hearc.fidarc.ui.network.FidarcAPI
import ch.hearc.fidarc.ui.network.FidarcAPIService
import kotlinx.android.synthetic.main.fragment_account_user.*
import kotlinx.android.synthetic.main.fragment_account_user.view.*
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

        accountView.button_account_log_out_user.setOnClickListener {
            val loginRepository = LoginRepository(
                dataSource = LoginDataSource()
            )
            loginRepository.logout(activity!!.baseContext)
            startActivity(Intent(activity, LoginActivity::class.java))
            activity?.finish()
        }

        return accountView
    }


    /**
     * Load the fidelity card on the recycler view and add user info in view
     *
     * @param view current view
     */
    private fun loadClientData(view:View) {

        val sharedPref = activity!!.getSharedPreferences("user", Context.MODE_PRIVATE) ?: return
        val token = sharedPref.getString("token", "-1")
        val firstname = sharedPref.getString("firstname", "firstname")
        val lastname = sharedPref.getString("lastname", "lastname")
        var email = sharedPref.getString("email", "email")


        GlobalScope.launch(Dispatchers.Main) {



            val fidelityCards = client.getFidelityCards("Bearer " + token!!).data // Read the data from the API
            Log.d("test", fidelityCards.toString())
            with(Dispatchers.Main) {
                fidelityCards.forEach {
                    fidelityCardList.add(it)

                    mAdapter?.notifyDataSetChanged()//update the adapter data*/
                }
            }
        }
        view.text_account_first_name_user.text = firstname
        view.text_account_last_name_user.text = lastname
        view.text_account_email_user.text = email

    }
}