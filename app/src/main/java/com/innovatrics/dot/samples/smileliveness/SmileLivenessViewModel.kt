package com.innovatrics.dot.samples.smileliveness

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class SmileLivenessViewModel(
    private val createUiResultUseCase: CreateUiResultUseCase,
) : ViewModel() {

    private val mutableState: MutableLiveData<SmileLivenessState> = MutableLiveData()
    val state: LiveData<SmileLivenessState> = mutableState

    fun initializeState() {
        mutableState.value = SmileLivenessState()
    }

    fun process(smileLivenessResult: com.innovatrics.dot.face.liveness.smile.SmileLivenessResult) {
        // At this point use the Digital Identity Service in order to evaluate the smile liveness score.
        // See: https://developers.innovatrics.com/digital-onboarding/technical/remote/dot-dis/latest/documentation/#_smile_liveness_check
        viewModelScope.launch {
            val result = createUiResultUseCase(smileLivenessResult)
            mutableState.value = state.value!!.copy(result = result)
        }
    }
}
