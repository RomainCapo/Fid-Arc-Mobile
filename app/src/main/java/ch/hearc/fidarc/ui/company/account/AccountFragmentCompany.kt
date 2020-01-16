package ch.hearc.fidarc.ui.company.account

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ch.hearc.fidarc.R
import ch.hearc.fidarc.ui.data.LoginDataSource
import ch.hearc.fidarc.ui.data.LoginRepository
import ch.hearc.fidarc.ui.data.model.Company
import ch.hearc.fidarc.ui.login.LoginActivity
import ch.hearc.fidarc.ui.network.FidarcAPI
import ch.hearc.fidarc.ui.network.FidarcAPIService
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_account_company.view.*

class AccountFragmentCompany : Fragment() {
    private var client: FidarcAPIService = FidarcAPI.retrofitService

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var accountView = inflater.inflate(R.layout.fragment_account_company, container, false)

        val sharedPref = activity!!.getSharedPreferences("user", Context.MODE_PRIVATE)


        accountView.text_account_first_name_company.text = sharedPref.getString("firstname", "firstname")
        accountView.text_account_last_name_company.text = sharedPref.getString("lastname", "lastname")
        accountView.text_account_email_company.text = sharedPref.getString("email", "email")

        val gson = Gson()
        val json = sharedPref.getString("company", "-1")
        val company = with(gson, { fromJson<Company>(json, Company::class.java!!) })

        accountView.text_account_name_company.text = company.company_name
        accountView.text_account_description_company.text = company.company_description
        accountView.text_number_of_fidelity_point_company.text = company.number_fidelity_points.toString()
        accountView.text_card_color_company.text = company.card_color_id.toString()
        accountView.text_message_to_user_company.text = company.message_to_user

        accountView.button_account_log_out_company.setOnClickListener {
            val loginRepository = LoginRepository(
                dataSource = LoginDataSource()
            )
            loginRepository.logout(activity!!.baseContext)
            startActivity(Intent(activity, LoginActivity::class.java))
            activity?.finish()
        }

        return accountView
    }
}