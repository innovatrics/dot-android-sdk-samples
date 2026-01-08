package com.innovatrics.dot.samples.faceautocapture.customui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.innovatrics.dot.face.autocapture.ui.BaseFaceAutoCaptureFragment
import com.innovatrics.dot.face.autocapture.ui.UiState
import com.innovatrics.dot.samples.DotSdkViewModel
import com.innovatrics.dot.samples.DotSdkViewModelFactory
import com.innovatrics.dot.samples.R
import com.innovatrics.dot.samples.faceautocapture.FaceAutoCaptureViewModel
import com.innovatrics.dot.samples.faceautocapture.FaceAutoCaptureViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CustomUiFaceAutoCaptureFragment : BaseFaceAutoCaptureFragment(contentLayoutId = R.layout.fragment_custom_ui_face_auto_capture) {

    private val dotSdkViewModel: DotSdkViewModel by activityViewModels { DotSdkViewModelFactory(requireActivity().application) }
    private val faceAutoCaptureViewModel: FaceAutoCaptureViewModel by activityViewModels { FaceAutoCaptureViewModelFactory() }

    private lateinit var phase: FaceAutoCapturePhase

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.setupPhase()
        setupDotSdkViewModel()
        setupFaceAutoCaptureViewModel()
    }

    private fun View.setupPhase() {
        phase = FaceAutoCapturePhase(
            context = requireContext(),
            textView = findViewById(R.id.phase),
        )
    }

    private fun setupDotSdkViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
                dotSdkViewModel.state.collectLatest { state ->
                    if (state.isInitialized) {
                        start()
                    }
                }
            }
        }
        dotSdkViewModel.initializeDotSdkIfNeeded()
    }

    private fun setupFaceAutoCaptureViewModel() {
        faceAutoCaptureViewModel.initializeState()
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
                faceAutoCaptureViewModel.state.collectLatest { state ->
                    state.result?.let {
                        findNavController().navigate(resId = R.id.action_CustomUiFaceAutoCaptureFragment_to_FaceAutoCaptureResultFragment)
                    }
                }
            }
        }
    }

    override fun provideBaseConfiguration(): Configuration {
        return Configuration()
    }

    override fun onNoCameraPermission() {
        throw IllegalStateException("No camera permission.")
    }

    override fun onUiStateUpdated(uiState: UiState) {
        phase.update(uiState)
        if (uiState is UiState.Running) {
            uiState.result?.let {
                faceAutoCaptureViewModel.process(faceAutoCaptureResult = it)
            }
        }
    }
}
