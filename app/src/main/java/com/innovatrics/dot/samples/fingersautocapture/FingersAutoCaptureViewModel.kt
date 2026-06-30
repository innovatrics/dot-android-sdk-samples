package com.innovatrics.dot.samples.fingersautocapture

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FingersAutoCaptureViewModel(
    private val createUiResultUseCase: CreateUiResultUseCase,
    private val transformFingersToFingerprints: TransformFingersToFingerprintsUseCase,
) : ViewModel() {

    data class State(
        val result: FingersAutoCaptureResult? = null,
    )

    private val mutableState = MutableStateFlow(State())
    val state = mutableState.asStateFlow()

    private var processingJob: Job? = null

    fun initializeState() {
        processingJob?.cancel()
        mutableState.update { it.copy(result = null) }
    }

    fun process(fingersAutoCaptureResult: com.innovatrics.dot.fingers.autocapture.FingersAutoCaptureResult) {
        processingJob = viewModelScope.launch {
            val resultBeforeTransformation = createUiResultUseCase(fingersAutoCaptureResult)
            mutableState.update { it.copy(result = resultBeforeTransformation) }
            val fingerprintBitmaps = transformFingersToFingerprints(fingerBundles = resultBeforeTransformation.fingersAutoCaptureResult.fingerBundles)
            val resultAfterTransformation = resultBeforeTransformation.copy(fingerprintBitmaps = fingerprintBitmaps)
            mutableState.update { it.copy(result = resultAfterTransformation) }
        }
    }
}
