package com.innovatrics.dot.samples.faceautocapture

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class FaceAutoCaptureViewModel(
    private val createUiResultUseCase: CreateUiResultUseCase,
) : ViewModel() {

    private val mutableState: MutableLiveData<FaceAutoCaptureState> = MutableLiveData()
    val state: LiveData<FaceAutoCaptureState> = mutableState

    fun initializeState() {
        mutableState.value = FaceAutoCaptureState()
    }

    fun process(faceAutoCaptureResult: com.innovatrics.dot.face.autocapture.FaceAutoCaptureResult) {
        viewModelScope.launch {
            mutableState.value = state.value!!.copy(isProcessing = true)
            mutableState.value = processSafely(faceAutoCaptureResult)
        }
    }

    private suspend fun processSafely(faceAutoCaptureResult: com.innovatrics.dot.face.autocapture.FaceAutoCaptureResult): FaceAutoCaptureState {
        return try {
            val result = createUiResultUseCase(faceAutoCaptureResult)
            state.value!!.copy(isProcessing = false, result = result)
        } catch (e: Exception) {
            state.value!!.copy(isProcessing = false, errorMessage = e.message)
        }
    }

    fun notifyErrorMessageShown() {
        mutableState.value = state.value!!.copy(errorMessage = null)
    }
}
