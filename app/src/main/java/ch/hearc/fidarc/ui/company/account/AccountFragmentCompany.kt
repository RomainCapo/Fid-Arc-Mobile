package ch.hearc.fidarc.ui.company.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ch.hearc.fidarc.R
import ch.hearc.fidarc.ui.network.FidarcAPI
import ch.hearc.fidarc.ui.network.FidarcAPIService

class AccountFragmentUser : Fragment() {
    private var client: FidarcAPIService = FidarcAPI.retrofitService

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var accountView = inflater.inflate(R.layout.fragment_account_user, container, false)

        return accountView
    }
}