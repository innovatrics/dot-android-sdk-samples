package com.innovatrics.dot.samples.nfcreading

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.innovatrics.dot.document.autocapture.DocumentAutoCaptureDetection
import com.innovatrics.dot.document.autocapture.DocumentAutoCaptureFragment
import com.innovatrics.dot.document.autocapture.DocumentAutoCaptureResult
import com.innovatrics.dot.samples.MainViewModel
import com.innovatrics.dot.samples.R

class NfcKeyCaptureFragment : DocumentAutoCaptureFragment() {

    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var nfcReadingViewModel: NfcReadingViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupNfcReadingViewModel()
        start()
    }

    private fun setupNfcReadingViewModel() {
        val nfcReadingViewModelFactory = NfcReadingViewModelFactory(requireActivity().application.resources)
        nfcReadingViewModel = ViewModelProvider(requireActivity(), nfcReadingViewModelFactory).get(NfcReadingViewModel::class.java)
        nfcReadingViewModel.initializeState()
        nfcReadingViewModel.state.observe(viewLifecycleOwner) { state ->
            state.nfcKey?.let {
                findNavController().navigate(R.id.action_NfcKeyCaptureFragment_to_StartNfcReadingFragment)
            }
        }
    }

    override fun onNoCameraPermission() {
        mainViewModel.notifyNoCameraPermission()
    }

    override fun onCandidateSelectionStarted() {
    }

    override fun onCaptured(result: DocumentAutoCaptureResult) {
        nfcReadingViewModel.resolveAndSetNfcKey(result.machineReadableZone)
    }

    override fun onDetected(detection: DocumentAutoCaptureDetection) {
    }
}
