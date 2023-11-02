package com.innovatrics.dot.samples

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.innovatrics.dot.core.DotSdk
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DotSdkViewModel(
    application: Application,
    private val initializeDotSdkUseCase: InitializeDotSdkUseCase,
) : AndroidViewModel(application) {

    private val mutableState = MutableStateFlow(DotSdkState())
    val state = mutableState.asStateFlow()

    fun initializeDotSdkIfNeeded() {
        viewModelScope.launch {
            initializeDotSdkIfNeededInternal()
        }
    }

    private suspend fun initializeDotSdkIfNeededInternal() {
        when (DotSdk.isInitialized()) {
            true -> mutableState.update { it.copy(isInitialized = true) }
            false -> initializeDotSdkSafely()
        }
    }

    private suspend fun initializeDotSdkSafely() = try {
        initializeDotSdkUseCase(getApplication())
        mutableState.update { it.copy(isInitialized = true) }
    } catch (e: Exception) {
        mutableState.update { it.copy(errorMessage = e.message) }
    }

    fun notifyErrorMessageShown() {
        mutableState.update { it.copy(errorMessage = null) }
    }
}
