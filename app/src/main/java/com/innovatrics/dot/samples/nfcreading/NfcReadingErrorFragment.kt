package com.innovatrics.dot.samples.nfcreading

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.innovatrics.dot.samples.R

class NfcReadingErrorFragment : Fragment(R.layout.fragment_nfc_reading_error) {

    private val nfcReadingViewModel: NfcReadingViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<TextView>(R.id.text).text = nfcReadingViewModel.state.value?.errorMessage!!
    }
}
