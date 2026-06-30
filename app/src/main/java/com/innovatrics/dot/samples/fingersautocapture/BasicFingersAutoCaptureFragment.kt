package com.innovatrics.dot.samples.fingersautocapture

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.innovatrics.dot.fingers.autocapture.FingersAutoCaptureResult
import com.innovatrics.dot.fingers.autocapture.ui.FingersAutoCaptureFragment
import com.innovatrics.dot.samples.DotSdkViewModel
import com.innovatrics.dot.samples.DotSdkViewModelFactory
import com.innovatrics.dot.samples.R
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class BasicFingersAutoCaptureFragment : FingersAutoCaptureFragment() {

    private val dotSdkViewModel: DotSdkViewModel by activityViewModels { DotSdkViewModelFactory(requireActivity().application) }
    private val fingersAutoCaptureViewModel: FingersAutoCaptureViewModel by activityViewModels { FingersAutoCaptureViewModelFactory() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupDotSdkViewModel()
        setupFingersAutoCaptureViewModel()
    }

    private fun setupDotSdkViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
                dotSdkViewModel.state.collectLatest { state ->
                    if (state.isInitialized) {
                        start()
                    }
                }
            }
        }
        dotSdkViewModel.initializeDotSdkIfNeeded()
    }

    private fun setupFingersAutoCaptureViewModel() {
        fingersAutoCaptureViewModel.initializeState()
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
                fingersAutoCaptureViewModel.state.collectLatest { state ->
                    state.result?.let {
                        findNavController().navigate(resId = R.id.action_BasicFingersAutoCaptureFragment_to_FingersAutoCaptureResultFragment)
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

    override fun onFinished(result: FingersAutoCaptureResult) {
        fingersAutoCaptureViewModel.process(result)
    }
}
