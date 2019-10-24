package ch.hearc.fidarc.ui.scan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ScanViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Please show the QRCode to the partner to get your fidelity point !"
    }
    val text: LiveData<String> = _text
}