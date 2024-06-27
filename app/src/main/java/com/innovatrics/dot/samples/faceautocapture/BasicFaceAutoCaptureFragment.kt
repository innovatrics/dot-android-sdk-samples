package com.innovatrics.dot.samples.faceautocapture

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.innovatrics.dot.face.autocapture.FaceAutoCaptureDetection
import com.innovatrics.dot.face.autocapture.FaceAutoCaptureFragment
import com.innovatrics.dot.face.autocapture.FaceAutoCaptureResult
import com.innovatrics.dot.face.detection.FaceDetectionQuery
import com.innovatrics.dot.face.quality.ExpressionQuery
import com.innovatrics.dot.face.quality.EyesExpressionQuery
import com.innovatrics.dot.face.quality.FaceImageQualityQuery
import com.innovatrics.dot.face.quality.FaceQualityQuery
import com.innovatrics.dot.face.quality.HeadPoseQuery
import com.innovatrics.dot.face.quality.WearablesQuery
import com.innovatrics.dot.samples.DotSdkViewModel
import com.innovatrics.dot.samples.DotSdkViewModelFactory
import com.innovatrics.dot.samples.MainViewModel
import com.innovatrics.dot.samples.R
import kotlinx.coroutines.launch

class BasicFaceAutoCaptureFragment : FaceAutoCaptureFragment() {

    private val mainViewModel: MainViewModel by activityViewModels()
    private val dotSdkViewModel: DotSdkViewModel by activityViewModels { DotSdkViewModelFactory(requireActivity().application) }
    private val faceAutoCaptureViewModel: FaceAutoCaptureViewModel by activityViewModels { FaceAutoCaptureViewModelFactory() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupDotSdkViewModel()
        setupFaceAutoCaptureViewModel()
    }

    override fun provideConfiguration() = Configuration(
        query = FaceDetectionQuery(
            faceQuality = FaceQualityQuery(
                imageQuality = FaceImageQualityQuery(
                    evaluateSharpness = true,
                    evaluateBrightness = true,
                    evaluateContrast = true,
                    evaluateUniqueIntensityLevels = true,
                    evaluateShadow = true,
                    evaluateSpecularity = true,
                ),
                headPose = HeadPoseQuery(
                    evaluateRoll = true,
                    evaluateYaw = true,
                    evaluatePitch = true,
                ),
                wearables = WearablesQuery(
                    evaluateGlasses = true,
                ),
                expression = ExpressionQuery(
                    eyes = EyesExpressionQuery(
                        evaluateRightEye = true,
                        evaluateLeftEye = true,
                    ),
                    evaluateMouth = true,
                ),
            ),
        ),
    )

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

    private fun setupFaceAutoCaptureViewModel() {
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

    override fun onCaptured(result: FaceAutoCaptureResult) {
        faceAutoCaptureViewModel.process(result)
    }

    override fun onProcessed(detection: FaceAutoCaptureDetection) {
    }
}
