package com.innovatrics.dot.samples.documentautocapture

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class DocumentAutoCaptureViewModel(
    private val createUiResultUseCase: CreateUiResultUseCase,
) : ViewModel() {

    private val mutableState: MutableLiveData<DocumentAutoCaptureState> = MutableLiveData()
    val state: LiveData<DocumentAutoCaptureState> = mutableState

    fun initializeState() {
        mutableState.value = DocumentAutoCaptureState()
    }

    fun process(documentAutoCaptureResult: com.innovatrics.dot.document.autocapture.DocumentAutoCaptureResult) {
        viewModelScope.launch {
            val result = createUiResultUseCase(documentAutoCaptureResult)
            mutableState.value = state.value!!.copy(result = result)
        }
    }
}
