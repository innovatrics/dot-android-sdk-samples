package com.innovatrics.dot.samples.palmautocapture

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.innovatrics.dot.palm.autocapture.PalmAutoCaptureResult
import kotlinx.coroutines.launch

class PalmAutoCaptureViewModel(
    private val createUiResultUseCase: CreateUiResultUseCase,
) : ViewModel() {

    private val mutableState: MutableLiveData<PalmAutoCaptureState> = MutableLiveData()
    val state: LiveData<PalmAutoCaptureState> = mutableState

    fun initializeState() {
        mutableState.value = PalmAutoCaptureState()
    }

    fun process(palmAutoCaptureResult: PalmAutoCaptureResult) {
        viewModelScope.launch {
            mutableState.value = state.value!!.copy(isProcessing = true)
            mutableState.value = processSafely(palmAutoCaptureResult)
        }
    }

    private suspend fun processSafely(palmAutoCaptureResult: PalmAutoCaptureResult): PalmAutoCaptureState {
        return try {
            val result = createUiResultUseCase(palmAutoCaptureResult)
            state.value!!.copy(isProcessing = false, result = result)
        } catch (e: Exception) {
            state.value!!.copy(isProcessing = false, errorMessage = e.message)
        }
    }

    fun notifyErrorMessageShown() {
        mutableState.value = state.value!!.copy(errorMessage = null)
    }
}
