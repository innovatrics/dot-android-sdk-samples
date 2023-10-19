package com.innovatrics.dot.samples.nfcreading

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.util.Consumer
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.innovatrics.dot.samples.MainViewModel
import com.innovatrics.dot.samples.R

class StartNfcReadingFragment : Fragment(R.layout.fragment_start_nfc_reading) {

    private val mainViewModel: MainViewModel by activityViewModels()
    private val nfcReadingViewModel: NfcReadingViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupNfcReadingViewModel()
        lifecycle.addObserver(
            StartNfcReadingFragmentObserver(
                activity = requireActivity(),
                onNewIntentListener = Consumer<Intent>(nfcReadingViewModel::read),
            ),
        )
    }

    private fun setupNfcReadingViewModel() {
        nfcReadingViewModel.state.observe(viewLifecycleOwner) { state ->
            mainViewModel.setProcessing(state.isReading)
            state.result?.let {
                findNavController().navigate(R.id.action_StartNfcReadingFragment_to_NfcReadingResultFragment)
            }
            state.errorMessage?.let {
                Snackbar.make(requireView(), it, Snackbar.LENGTH_SHORT).show()
                nfcReadingViewModel.notifyErrorMessageShown()
            }
        }
    }
}
