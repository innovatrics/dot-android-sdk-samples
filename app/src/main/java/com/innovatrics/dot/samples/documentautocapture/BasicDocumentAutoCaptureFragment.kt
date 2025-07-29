package com.innovatrics.dot.samples.documentautocapture

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
import com.innovatrics.dot.samples.DotSdkViewModel
import com.innovatrics.dot.samples.DotSdkViewModelFactory
import com.innovatrics.dot.samples.R
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class BasicDocumentAutoCaptureFragment : DocumentAutoCaptureFragment() {

    private val dotSdkViewModel: DotSdkViewModel by activityViewModels { DotSdkViewModelFactory(requireActivity().application) }
    private val documentAutoCaptureViewModel: DocumentAutoCaptureViewModel by activityViewModels { DocumentAutoCaptureViewModelFactory() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupDotSdkViewModel()
        setupDocumentAutoCaptureViewModel()
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

    private fun setupDocumentAutoCaptureViewModel() {
        documentAutoCaptureViewModel.initializeState()
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                documentAutoCaptureViewModel.state.collectLatest { state ->
                    state.result?.let {
                        findNavController().navigate(R.id.action_BasicDocumentAutoCaptureFragment_to_DocumentAutoCaptureResultFragment)
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

    override fun onProcessed(detection: DocumentAutoCaptureDetection) {
    }

    override fun onCaptured(result: DocumentAutoCaptureResult) {
        documentAutoCaptureViewModel.process(result)
    }
}
