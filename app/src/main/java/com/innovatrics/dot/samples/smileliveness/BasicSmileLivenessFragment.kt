package com.innovatrics.dot.samples.smileliveness

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.innovatrics.dot.face.autocapture.FaceAutoCaptureDetection
import com.innovatrics.dot.face.liveness.smile.SmileLivenessFragment
import com.innovatrics.dot.face.liveness.smile.SmileLivenessResult
import com.innovatrics.dot.samples.MainViewModel
import com.innovatrics.dot.samples.R
import com.innovatrics.dot.samples.face.DotFaceViewModel
import com.innovatrics.dot.samples.face.DotFaceViewModelFactory

class BasicSmileLivenessFragment : SmileLivenessFragment() {

    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var dotFaceViewModel: DotFaceViewModel
    private val smileLivenessViewModel: SmileLivenessViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupDotFaceViewModel()
        setupSmileLivenessViewModel()
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

    private fun setupSmileLivenessViewModel() {
        smileLivenessViewModel.initializeState()
        smileLivenessViewModel.state.observe(viewLifecycleOwner) { state ->
            state.result?.let {
                findNavController().navigate(R.id.action_BasicSmileLivenessFragment_to_SmileLivenessResultFragment)
            }
        }
    }

    override fun onNoCameraPermission() {
        mainViewModel.notifyNoCameraPermission()
    }

    override fun onCriticalFacePresenceLost() {
    }

    override fun onProcessed(detection: FaceAutoCaptureDetection) {
    }

    override fun onFinished(result: SmileLivenessResult) {
        smileLivenessViewModel.process(result)
    }

    override fun onStopped() {
    }
}
