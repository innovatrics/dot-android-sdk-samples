package com.innovatrics.dot.samples.documentautocapture

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.innovatrics.dot.document.autocapture.DocumentAutoCaptureDetection
import com.innovatrics.dot.document.autocapture.DocumentAutoCaptureFragment
import com.innovatrics.dot.document.autocapture.DocumentAutoCaptureResult
import com.innovatrics.dot.samples.MainViewModel
import com.innovatrics.dot.samples.R

class BasicDocumentAutoCaptureFragment : DocumentAutoCaptureFragment() {

    private val mainViewModel: MainViewModel by activityViewModels()
    private val documentAutoCaptureViewModel: DocumentAutoCaptureViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupDocumentAutoCaptureViewModel()
        start()
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

    override fun onDetected(detection: DocumentAutoCaptureDetection) {
    }
}
