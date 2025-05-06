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
            val result = createUiResultUseCase(faceAutoCaptureResult)
            mutableState.value = state.value!!.copy(isProcessing = false, result = result)
        }
    }
}
