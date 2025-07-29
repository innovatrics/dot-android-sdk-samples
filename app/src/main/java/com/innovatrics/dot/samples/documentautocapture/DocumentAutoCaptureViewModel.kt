package com.innovatrics.dot.samples.documentautocapture

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DocumentAutoCaptureViewModel(
    private val createUiResultUseCase: CreateUiResultUseCase,
) : ViewModel() {

    data class State(
        val result: DocumentAutoCaptureResult? = null,
    )

    private val mutableState = MutableStateFlow(State())
    val state = mutableState.asStateFlow()

    fun initializeState() {
        mutableState.update { it.copy(result = null) }
    }

    fun process(documentAutoCaptureResult: com.innovatrics.dot.document.autocapture.DocumentAutoCaptureResult) {
        viewModelScope.launch {
            val result = createUiResultUseCase(documentAutoCaptureResult)
            mutableState.update { it.copy(result = result) }
        }
    }
}
