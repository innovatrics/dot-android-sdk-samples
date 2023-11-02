package com.innovatrics.dot.samples.documentautocapture

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.innovatrics.dot.document.autocapture.DocumentAutoCaptureDetection
import com.innovatrics.dot.document.autocapture.DocumentAutoCaptureFragment
import com.innovatrics.dot.document.autocapture.DocumentAutoCaptureResult
import com.innovatrics.dot.samples.DotSdkViewModel
import com.innovatrics.dot.samples.DotSdkViewModelFactory
import com.innovatrics.dot.samples.MainViewModel
import com.innovatrics.dot.samples.R
import kotlinx.coroutines.launch

class BasicDocumentAutoCaptureFragment : DocumentAutoCaptureFragment() {

    private val mainViewModel: MainViewModel by activityViewModels()
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
                    state.errorMessage?.let {
                        Snackbar.make(requireView(), it, Snackbar.LENGTH_SHORT).show()
                        dotSdkViewModel.notifyErrorMessageShown()
                    }
                }
            }
        }
        dotSdkViewModel.initializeDotSdkIfNeeded()
    }

    private fun setupDocumentAutoCaptureViewModel() {
        documentAutoCaptureViewModel.initializeState()
        documentAutoCaptureViewModel.state.observe(viewLifecycleOwner) { state ->
            state.result?.let {
                findNavController().navigate(R.id.action_BasicDocumentAutoCaptureFragment_to_DocumentAutoCaptureResultFragment)
            }
        }
    }

    override fun onNoCameraPermission() {
        mainViewModel.notifyNoCameraPermission()
    }

    override fun onCandidateSelectionStarted() {
    }

    override fun onCaptured(result: DocumentAutoCaptureResult) {
        documentAutoCaptureViewModel.process(result)
    }

    override fun onProcessed(detection: DocumentAutoCaptureDetection) {
    }

    override fun onStopped() {
    }
}
