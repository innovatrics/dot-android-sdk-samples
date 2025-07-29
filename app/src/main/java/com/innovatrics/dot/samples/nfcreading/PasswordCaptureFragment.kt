package com.innovatrics.dot.samples.nfcreading

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.innovatrics.dot.document.autocapture.DocumentAutoCaptureDetection
import com.innovatrics.dot.document.autocapture.DocumentAutoCaptureFragment
import com.innovatrics.dot.document.autocapture.DocumentAutoCaptureResult
import com.innovatrics.dot.document.autocapture.MrzValidation
import com.innovatrics.dot.samples.DotSdkViewModel
import com.innovatrics.dot.samples.DotSdkViewModelFactory
import com.innovatrics.dot.samples.R
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PasswordCaptureFragment : DocumentAutoCaptureFragment() {

    private val dotSdkViewModel: DotSdkViewModel by activityViewModels { DotSdkViewModelFactory(requireActivity().application) }
    private val nfcReadingViewModel: NfcReadingViewModel by activityViewModels { NfcReadingViewModelFactory(requireActivity().application) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupDotSdkViewModel()
        setupNfcReadingViewModel()
    }

    private fun setupDotSdkViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                dotSdkViewModel.state.collect { state ->
                    if (state.isInitialized) {
                        start()
                    }
                }
            }
        }
        dotSdkViewModel.initializeDotSdkIfNeeded()
    }

    private fun setupNfcReadingViewModel() {
        nfcReadingViewModel.initializeState()
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                nfcReadingViewModel.state.collectLatest { state ->
                    state.configuration?.let {
                        findNavController().navigate(R.id.action_PasswordCaptureFragment_to_NfcReadingFragment)
                    }
                }
            }
        }
    }

    override fun provideConfiguration(): Configuration {
        return Configuration(
            mrzValidation = MrzValidation.VALIDATE_ALWAYS,
        )
    }

    override fun onNoCameraPermission() {
        throw IllegalStateException("No camera permission.")
    }

    override fun onProcessed(detection: DocumentAutoCaptureDetection) {
    }

    override fun onCaptured(result: DocumentAutoCaptureResult) {
        nfcReadingViewModel.setupConfiguration(result.machineReadableZone!!)
    }
}
