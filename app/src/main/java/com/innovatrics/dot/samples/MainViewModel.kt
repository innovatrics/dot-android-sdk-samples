package com.innovatrics.dot.samples

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private val mutableState: MutableLiveData<MainState> = MutableLiveData<MainState>().apply { value = MainState() }
    val state: LiveData<MainState> = mutableState

    fun setProcessing(enabled: Boolean) {
        mutableState.value = state.value!!.copy(isProcessing = enabled)
    }

    fun setIntent(intent: Intent?) {
        mutableState.value = state.value!!.copy(intent = intent)
    }

    fun notifyIntentConsumed() {
        mutableState.value = state.value!!.copy(intent = null)
    }

    fun notifyNoCameraPermission() {
        mutableState.value = state.value!!.copy(errorMessage = "No camera permission.")
    }

    fun notifyErrorMessageShown() {
        mutableState.value = state.value!!.copy(errorMessage = null)
    }
}
