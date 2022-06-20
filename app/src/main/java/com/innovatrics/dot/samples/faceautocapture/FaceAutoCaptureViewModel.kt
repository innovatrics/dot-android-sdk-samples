package com.innovatrics.dot.samples.faceautocapture

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.innovatrics.dot.face.detection.DetectedFace
import kotlinx.coroutines.launch

class FaceAutoCaptureViewModel(
    private val processDetectedFaceUseCase: ProcessDetectedFaceUseCase,
) : ViewModel() {

    private val mutableState: MutableLiveData<FaceAutoCaptureState> = MutableLiveData()
    val state: LiveData<FaceAutoCaptureState> = mutableState

    fun initializeState() {
        mutableState.value = FaceAutoCaptureState()
    }

    fun process(detectedFace: DetectedFace) {
        viewModelScope.launch {
            mutableState.value = state.value!!.copy(isProcessing = true)
            mutableState.value = processSafely(detectedFace)
        }
    }

    private suspend fun processSafely(detectedFace: DetectedFace): FaceAutoCaptureState {
        return try {
            val result = processDetectedFaceUseCase(detectedFace)
            state.value!!.copy(isProcessing = false, result = result)
        } catch (e: Exception) {
            state.value!!.copy(isProcessing = false, errorMessage = e.message)
        }
    }

    fun notifyErrorMessageShown() {
        mutableState.value = state.value!!.copy(errorMessage = null)
    }
}
