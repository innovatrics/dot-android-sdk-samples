package com.innovatrics.dot.samples

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private val mutableState: MutableLiveData<MainState> = MutableLiveData<MainState>().apply { value = MainState() }
    val state: LiveData<MainState> = mutableState

    fun setProcessing(enabled: Boolean) {
        mutableState.value = state.value!!.copy(isProcessing = enabled)
    }

    fun notifyNoCameraPermission() {
        throw IllegalStateException("No camera permission.")
    }
}
