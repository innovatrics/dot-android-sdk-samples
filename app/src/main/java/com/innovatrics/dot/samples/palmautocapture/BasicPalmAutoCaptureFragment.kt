package com.innovatrics.dot.samples.palmautocapture

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.innovatrics.dot.palm.autocapture.PalmAutoCaptureDetection
import com.innovatrics.dot.palm.autocapture.PalmAutoCaptureResult
import com.innovatrics.dot.palm.autocapture.ui.PalmAutoCaptureFragment
import com.innovatrics.dot.samples.DotSdkViewModel
import com.innovatrics.dot.samples.DotSdkViewModelFactory
import com.innovatrics.dot.samples.MainViewModel
import com.innovatrics.dot.samples.R
import kotlinx.coroutines.launch

class BasicPalmAutoCaptureFragment : PalmAutoCaptureFragment() {

    private val mainViewModel: MainViewModel by activityViewModels()
    private val dotSdkViewModel: DotSdkViewModel by activityViewModels { DotSdkViewModelFactory(requireActivity().application) }
    private val palmAutoCaptureViewModel: PalmAutoCaptureViewModel by activityViewModels { PalmAutoCaptureViewModelFactory() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupDotSdkViewModel()
        setupPalmAutoCaptureViewModel()
    }

    override fun onNoCameraPermission() {
        mainViewModel.notifyNoCameraPermission()
    }

    override fun onCaptured(result: PalmAutoCaptureResult) {
        palmAutoCaptureViewModel.process(result)
    }

    override fun onProcessed(detection: PalmAutoCaptureDetection) {}

    override fun provideConfiguration(): Configuration = Configuration()

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

    private fun setupPalmAutoCaptureViewModel() {
        palmAutoCaptureViewModel.initializeState()
        palmAutoCaptureViewModel.state.observe(viewLifecycleOwner) { state ->
            mainViewModel.setProcessing(state.isProcessing)
            state.result?.let {
                findNavController().navigate(R.id.action_BasicPalmAutoCaptureFragment_to_PalmAutoCaptureResultFragment)
            }
            state.errorMessage?.let {
                Snackbar.make(requireView(), it, Snackbar.LENGTH_SHORT).show()
                palmAutoCaptureViewModel.notifyErrorMessageShown()
            }
        }
    }
}
