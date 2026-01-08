package com.innovatrics.dot.samples.magnifeyeliveness

import com.innovatrics.dot.image.BitmapFactory
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

typealias SdkMagnifEyeLivenessResult = com.innovatrics.dot.face.liveness.magnifeye.MagnifEyeLivenessResult

class CreateUiResultUseCase(
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default,
) {

    suspend operator fun invoke(sdkMagnifEyeLivenessResult: SdkMagnifEyeLivenessResult): MagnifEyeLivenessResult = withContext(context = defaultDispatcher) {
        MagnifEyeLivenessResult(
            bitmap = BitmapFactory.create(sdkMagnifEyeLivenessResult.image),
        )
    }
}
