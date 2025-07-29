package com.innovatrics.dot.samples.faceautocapture

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FaceAutoCaptureViewModel(
    private val createUiResultUseCase: CreateUiResultUseCase,
) : ViewModel() {

    data class State(
        val result: FaceAutoCaptureResult? = null,
    )

    private val mutableState = MutableStateFlow(State())
    val state = mutableState.asStateFlow()

    fun initializeState() {
        mutableState.update { it.copy(result = null) }
    }

    fun process(faceAutoCaptureResult: com.innovatrics.dot.face.autocapture.FaceAutoCaptureResult) {
        viewModelScope.launch {
            val result = createUiResultUseCase(faceAutoCaptureResult)
            mutableState.update { it.copy(result = result) }
        }
    }
}
