package com.innovatrics.dot.samples.smileliveness

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.innovatrics.dot.face.image.BitmapFactory

class SmileLivenessViewModel : ViewModel() {

    private val mutableState: MutableLiveData<SmileLivenessState> = MutableLiveData()
    val state: LiveData<SmileLivenessState> = mutableState

    fun initializeState() {
        mutableState.value = SmileLivenessState()
    }

    fun process(smileLivenessResult: com.innovatrics.dot.face.liveness.smile.SmileLivenessResult) {
        // At this point use the Digital Identity Service in order to evaluate the smile liveness score.
        // See: https://developers.innovatrics.com/digital-onboarding/technical/remote/dot-dis/latest/documentation/#_smile_liveness_check
        val uiResult = createUiResult(smileLivenessResult)
        mutableState.value = state.value!!.copy(result = uiResult)
    }

    private fun createUiResult(smileLivenessResult: com.innovatrics.dot.face.liveness.smile.SmileLivenessResult): SmileLivenessResult {
        return SmileLivenessResult(
            neutralExpressionBitmap = BitmapFactory.create(smileLivenessResult.neutralExpressionFace.image),
            smileExpressionBitmap = BitmapFactory.create(smileLivenessResult.smileExpressionFace.image),
        )
    }
}
