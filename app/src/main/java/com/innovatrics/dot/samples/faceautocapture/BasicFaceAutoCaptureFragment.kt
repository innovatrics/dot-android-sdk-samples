package com.innovatrics.dot.samples.faceautocapture

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.innovatrics.dot.face.autocapture.FaceAutoCaptureFragment
import com.innovatrics.dot.face.autocapture.steps.CaptureStepId
import com.innovatrics.dot.face.detection.DetectedFace
import com.innovatrics.dot.samples.MainViewModel
import com.innovatrics.dot.samples.R
import com.innovatrics.dot.samples.face.DotFaceViewModel
import com.innovatrics.dot.samples.face.DotFaceViewModelFactory

class BasicFaceAutoCaptureFragment : FaceAutoCaptureFragment() {

    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var dotFaceViewModel: DotFaceViewModel
    private lateinit var faceAutoCaptureViewModel: FaceAutoCaptureViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupDotFaceViewModel()
        setupFaceAutoCaptureViewModel()
    }

    private fun setupDotFaceViewModel() {
        val dotFaceViewModelFactory = DotFaceViewModelFactory(requireActivity().application)
        dotFaceViewModel = ViewModelProvider(this, dotFaceViewModelFactory)[DotFaceViewModel::class.java]
        dotFaceViewModel.state.observe(viewLifecycleOwner) { state ->
            if (state.isInitialized) {
                start()
            }
            state.errorMessage?.let {
                Snackbar.make(requireView(), it, Snackbar.LENGTH_SHORT).show()
                dotFaceViewModel.notifyErrorMessageShown()
            }
        }
        dotFaceViewModel.initializeDotFaceIfNeeded()
    }

    private fun setupFaceAutoCaptureViewModel() {
        val faceAutoCaptureViewModelFactory = FaceAutoCaptureViewModelFactory()
        faceAutoCaptureViewModel = ViewModelProvider(requireActivity(), faceAutoCaptureViewModelFactory)[FaceAutoCaptureViewModel::class.java]
        faceAutoCaptureViewModel.initializeState()
        faceAutoCaptureViewModel.state.observe(viewLifecycleOwner) { state ->
            mainViewModel.setProcessing(state.isProcessing)
            state.result?.let {
                findNavController().navigate(R.id.action_BasicFaceAutoCaptureFragment_to_FaceAutoCaptureResultFragment)
            }
            state.errorMessage?.let {
                Snackbar.make(requireView(), it, Snackbar.LENGTH_SHORT).show()
                faceAutoCaptureViewModel.notifyErrorMessageShown()
            }
        }
    }

    override fun onNoCameraPermission() {
        mainViewModel.notifyNoCameraPermission()
    }

    override fun onStepChanged(captureStepId: CaptureStepId, detectedFace: DetectedFace) {
    }

    override fun onCaptured(detectedFace: DetectedFace) {
        faceAutoCaptureViewModel.process(detectedFace)
    }

    override fun onStopped() {
    }
}
