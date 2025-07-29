package com.innovatrics.dot.samples.smileliveness

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SmileLivenessViewModel(
    private val createUiResultUseCase: CreateUiResultUseCase,
) : ViewModel() {

    data class State(
        val result: SmileLivenessResult? = null,
    )

    private val mutableState = MutableStateFlow(State())
    val state = mutableState.asStateFlow()

    fun initializeState() {
        mutableState.update { it.copy(result = null) }
    }

    fun process(smileLivenessResult: com.innovatrics.dot.face.liveness.smile.SmileLivenessResult) {
        // At this point use the Digital Identity Service in order to evaluate the smile liveness score.
        // See: https://developers.innovatrics.com/digital-onboarding/technical/remote/dot-dis/latest/documentation/#_smile_liveness_check
        viewModelScope.launch {
            val result = createUiResultUseCase(smileLivenessResult)
            mutableState.update { it.copy(result = result) }
        }
    }
}
