package com.innovatrics.dot.samples.magnifeyeliveness

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.innovatrics.dot.face.autocapture.FaceAutoCaptureDetection
import com.innovatrics.dot.face.liveness.magnifeye.MagnifEyeLivenessFragment
import com.innovatrics.dot.face.liveness.magnifeye.MagnifEyeLivenessResult
import com.innovatrics.dot.samples.DotSdkViewModel
import com.innovatrics.dot.samples.DotSdkViewModelFactory
import com.innovatrics.dot.samples.MainViewModel
import com.innovatrics.dot.samples.R
import kotlinx.coroutines.launch

class BasicMagnifEyeLivenessFragment : MagnifEyeLivenessFragment() {

    private val mainViewModel: MainViewModel by activityViewModels()
    private val dotSdkViewModel: DotSdkViewModel by activityViewModels { DotSdkViewModelFactory(requireActivity().application) }
    private val magnifEyeLivenessViewModel: MagnifEyeLivenessViewModel by activityViewModels { MagnifEyeLivenessViewModelFactory() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupDotSdkViewModel()
        setupMagnifEyeLivenessViewModel()
    }

    override fun provideConfiguration(): Configuration {
        return Configuration()
    }

    private fun setupDotSdkViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                dotSdkViewModel.state.collect { state ->
                    if (state.isInitialized) {
                        start()
                    }
                    state.errorMessage?.let {
                        Snackbar.make(requireView(), it, Snackbar.LENGTH_SHORT).show()
                        dotSdkViewModel.notifyErrorMessageShown()
                    }
                }
            }
        }
        dotSdkViewModel.initializeDotSdkIfNeeded()
    }

    private fun setupMagnifEyeLivenessViewModel() {
        magnifEyeLivenessViewModel.initializeState()
        magnifEyeLivenessViewModel.state.observe(viewLifecycleOwner) { state ->
            state.result?.let {
                findNavController().navigate(R.id.action_BasicMagnifEyeLivenessFragment_to_MagnifEyeLivenessResultFragment)
            }
        }
    }

    override fun onFinished(result: MagnifEyeLivenessResult) {
        magnifEyeLivenessViewModel.process(result)
    }

    override fun onNoCameraPermission() {
        mainViewModel.notifyNoCameraPermission()
    }

    override fun onProcessed(detection: FaceAutoCaptureDetection) {
    }
}
