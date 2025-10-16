package com.innovatrics.dot.samples.multirangeliveness

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MultiRangeLivenessViewModel(
    private val createUiResultUseCase: CreateUiResultUseCase,
) : ViewModel() {

    data class State(
        val result: MultiRangeLivenessResult? = null,
    )

    private val mutableState = MutableStateFlow(State())
    val state = mutableState.asStateFlow()

    fun initializeState() {
        mutableState.update { it.copy(result = null) }
    }

    fun process(multiRangeLivenessResult: SdkMultiRangeLivenessResult) {
        // At this point use the Digital Identity Service in order to evaluate the MultiRange Liveness score.
        // See: https://developers.innovatrics.com/digital-onboarding/technical/remote/dot-dis/latest/documentation
        viewModelScope.launch {
            val result = createUiResultUseCase(multiRangeLivenessResult)
            mutableState.update { it.copy(result = result) }
        }
    }
}
