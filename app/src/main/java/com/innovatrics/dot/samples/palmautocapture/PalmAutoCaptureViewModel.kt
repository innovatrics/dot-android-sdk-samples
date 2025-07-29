package com.innovatrics.dot.samples.palmautocapture

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PalmAutoCaptureViewModel(
    private val createUiResultUseCase: CreateUiResultUseCase,
) : ViewModel() {

    data class State(
        val result: PalmAutoCaptureResult? = null,
    )

    private val mutableState = MutableStateFlow(State())
    val state = mutableState.asStateFlow()

    fun initializeState() {
        mutableState.update { it.copy(result = null) }
    }

    fun process(palmAutoCaptureResult: com.innovatrics.dot.palm.autocapture.PalmAutoCaptureResult) {
        viewModelScope.launch {
            val result = createUiResultUseCase(palmAutoCaptureResult)
            mutableState.update { it.copy(result = result) }
        }
    }
}
