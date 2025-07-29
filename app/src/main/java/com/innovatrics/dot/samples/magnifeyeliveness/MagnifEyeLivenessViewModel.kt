package com.innovatrics.dot.samples.magnifeyeliveness

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MagnifEyeLivenessViewModel(
    private val createUiResultUseCase: CreateUiResultUseCase,
) : ViewModel() {

    data class State(
        val result: MagnifEyeLivenessResult? = null,
    )

    private val mutableState = MutableStateFlow(State())
    val state = mutableState.asStateFlow()

    fun initializeState() {
        mutableState.update { it.copy(result = null) }
    }

    fun process(magnifEyeLivenessResult: com.innovatrics.dot.face.liveness.magnifeye.MagnifEyeLivenessResult) {
        // At this point use the Digital Identity Service in order to evaluate the MagnifEye liveness score.
        // See: https://developers.innovatrics.com/digital-onboarding/technical/remote/dot-dis/latest/documentation/#_magnifeye_liveness_check
        viewModelScope.launch {
            val result = createUiResultUseCase(magnifEyeLivenessResult)
            mutableState.update { it.copy(result = result) }
        }
    }
}
