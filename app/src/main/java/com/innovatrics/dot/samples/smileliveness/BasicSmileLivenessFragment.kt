package com.innovatrics.dot.samples.smileliveness

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.innovatrics.dot.face.autocapture.FaceAutoCaptureDetection
import com.innovatrics.dot.face.liveness.smile.SmileLivenessFragment
import com.innovatrics.dot.face.liveness.smile.SmileLivenessResult
import com.innovatrics.dot.samples.DotSdkViewModel
import com.innovatrics.dot.samples.DotSdkViewModelFactory
import com.innovatrics.dot.samples.R
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class BasicSmileLivenessFragment : SmileLivenessFragment() {

    private val dotSdkViewModel: DotSdkViewModel by activityViewModels { DotSdkViewModelFactory(requireActivity().application) }
    private val smileLivenessViewModel: SmileLivenessViewModel by activityViewModels { SmileLivenessViewModelFactory() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupDotSdkViewModel()
        setupSmileLivenessViewModel()
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

    private fun setupSmileLivenessViewModel() {
        smileLivenessViewModel.initializeState()
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                smileLivenessViewModel.state.collectLatest { state ->
                    state.result?.let {
                        findNavController().navigate(R.id.action_BasicSmileLivenessFragment_to_SmileLivenessResultFragment)
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

    override fun onCriticalFacePresenceLost() {
    }

    override fun onProcessed(detection: FaceAutoCaptureDetection) {
    }

    override fun onFinished(result: SmileLivenessResult) {
        smileLivenessViewModel.process(result)
    }
}
