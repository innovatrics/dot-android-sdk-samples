package com.innovatrics.dot.samples.magnifeyeliveness

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MagnifEyeLivenessViewModel(
    private val createUiResultUseCase: CreateUiResultUseCase,
) : ViewModel() {

    private val mutableState: MutableLiveData<MagnifEyeLivenessState> = MutableLiveData()
    val state: LiveData<MagnifEyeLivenessState> = mutableState

    fun initializeState() {
        mutableState.value = MagnifEyeLivenessState()
    }

    fun process(magnifEyeLivenessResult: com.innovatrics.dot.face.liveness.magnifeye.MagnifEyeLivenessResult) {
        // At this point use the Digital Identity Service in order to evaluate the MagnifEye liveness score.
        // See: https://developers.innovatrics.com/digital-onboarding/technical/remote/dot-dis/latest/documentation/#_magnifeye_liveness_check
        viewModelScope.launch {
            val result = createUiResultUseCase(magnifEyeLivenessResult)
            mutableState.value = state.value!!.copy(result = result)
        }
    }
}
