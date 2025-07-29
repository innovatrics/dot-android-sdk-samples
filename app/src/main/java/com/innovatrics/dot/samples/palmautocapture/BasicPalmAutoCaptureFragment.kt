package com.innovatrics.dot.samples.palmautocapture

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.innovatrics.dot.palm.autocapture.PalmAutoCaptureDetection
import com.innovatrics.dot.palm.autocapture.PalmAutoCaptureResult
import com.innovatrics.dot.palm.autocapture.ui.PalmAutoCaptureFragment
import com.innovatrics.dot.samples.DotSdkViewModel
import com.innovatrics.dot.samples.DotSdkViewModelFactory
import com.innovatrics.dot.samples.R
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class BasicPalmAutoCaptureFragment : PalmAutoCaptureFragment() {

    private val dotSdkViewModel: DotSdkViewModel by activityViewModels { DotSdkViewModelFactory(requireActivity().application) }
    private val palmAutoCaptureViewModel: PalmAutoCaptureViewModel by activityViewModels { PalmAutoCaptureViewModelFactory() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupDotSdkViewModel()
        setupPalmAutoCaptureViewModel()
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

    private fun setupPalmAutoCaptureViewModel() {
        palmAutoCaptureViewModel.initializeState()
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                palmAutoCaptureViewModel.state.collectLatest { state ->
                    state.result?.let {
                        findNavController().navigate(R.id.action_BasicPalmAutoCaptureFragment_to_PalmAutoCaptureResultFragment)
                    }
                }
            }
        }
    }

    override fun provideConfiguration(): Configuration {
        return Configuration()
    }

    override fun onNoCameraPermission() {
        throw IllegalStateException("No camera permission.")
    }

    override fun onProcessed(detection: PalmAutoCaptureDetection) {
    }

    override fun onCaptured(result: PalmAutoCaptureResult) {
        palmAutoCaptureViewModel.process(result)
    }
}
