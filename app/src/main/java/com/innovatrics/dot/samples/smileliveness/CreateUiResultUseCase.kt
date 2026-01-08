package com.innovatrics.dot.samples.smileliveness

import com.innovatrics.dot.image.BitmapFactory
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

typealias SdkSmileLivenessResult = com.innovatrics.dot.face.liveness.smile.SmileLivenessResult

class CreateUiResultUseCase(
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default,
) {

    suspend operator fun invoke(sdkSmileLivenessResult: SdkSmileLivenessResult): SmileLivenessResult = withContext(context = defaultDispatcher) {
        SmileLivenessResult(
            bitmap = BitmapFactory.create(sdkSmileLivenessResult.image),
        )
    }
}
