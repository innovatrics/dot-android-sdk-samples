package com.innovatrics.dot.samples.nfcreading

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.navigation.fragment.findNavController
import com.innovatrics.dot.nfc.reader.ui.NfcTravelDocumentReaderFragment
import com.innovatrics.dot.samples.R

class NfcReadingFragment : Fragment(R.layout.fragment_nfc_reading) {

    private val nfcReadingViewModel: NfcReadingViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.button_cancel).setOnClickListener { findNavController().popBackStack() }
        initNfcTravelDocumentReaderFragment(savedInstanceState)
        setupNfcReadingViewModel()
    }

    private fun initNfcTravelDocumentReaderFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            val arguments = Bundle().apply {
                putSerializable(NfcTravelDocumentReaderFragment.CONFIGURATION, nfcReadingViewModel.state.value!!.configuration!!)
            }
            parentFragmentManager.commit {
                replace(R.id.container, DefaultNfcTravelDocumentReaderFragment::class.java, arguments)
            }
        }
    }

    private fun setupNfcReadingViewModel() {
        nfcReadingViewModel.state.observe(viewLifecycleOwner) { state ->
            state.result?.let {
                findNavController().navigate(R.id.action_NfcReadingFragment_to_NfcReadingResultFragment)
            }
            state.errorMessage?.let {
                findNavController().navigate(R.id.action_NfcReadingFragment_to_NfcReadingErrorFragment)
            }
        }
    }
}
