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

    data class State(
        val isInitialized: Boolean = false,
    )

    private val mutableState = MutableStateFlow(State())
    val state = mutableState.asStateFlow()

    fun initializeDotSdkIfNeeded() {
        viewModelScope.launch {
            initializeDotSdkIfNeededInternal()
        }
    }

    private suspend fun initializeDotSdkIfNeededInternal() {
        if (DotSdk.isInitialized().not()) {
            initializeDotSdkUseCase(context = getApplication())
        }
        mutableState.update { it.copy(isInitialized = true) }
    }
}
