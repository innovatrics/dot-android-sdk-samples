package com.innovatrics.dot.samples.magnifeyeliveness

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.innovatrics.dot.face.autocapture.FaceAutoCaptureDetection
import com.innovatrics.dot.face.liveness.magnifeye.MagnifEyeLivenessFragment
import com.innovatrics.dot.face.liveness.magnifeye.MagnifEyeLivenessResult
import com.innovatrics.dot.samples.MainViewModel
import com.innovatrics.dot.samples.R
import com.innovatrics.dot.samples.face.DotFaceViewModel
import com.innovatrics.dot.samples.face.DotFaceViewModelFactory

class BasicMagnifEyeLivenessFragment : MagnifEyeLivenessFragment() {

    private val mainViewModel: MainViewModel by activityViewModels()
    private val dotFaceViewModel: DotFaceViewModel by activityViewModels { DotFaceViewModelFactory(requireActivity().application) }
    private val magnifEyeLivenessViewModel: MagnifEyeLivenessViewModel by activityViewModels { MagnifEyeLivenessViewModelFactory() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupDotFaceViewModel()
        setupMagnifEyeLivenessViewModel()
    }

    private fun setupDotFaceViewModel() {
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

    override fun onStopped() {
    }
}
