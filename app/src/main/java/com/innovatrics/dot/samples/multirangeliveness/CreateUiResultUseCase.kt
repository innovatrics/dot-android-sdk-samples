package com.innovatrics.dot.samples.multirangeliveness

import com.innovatrics.dot.image.BitmapFactory
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

typealias SdkMultiRangeLivenessResult = com.innovatrics.dot.face.liveness.multirange.MultiRangeLivenessResult

class CreateUiResultUseCase(
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default,
) {

    suspend operator fun invoke(sdkMultiRangeLivenessResult: SdkMultiRangeLivenessResult): MultiRangeLivenessResult = withContext(context = defaultDispatcher) {
        MultiRangeLivenessResult(
            bitmap = BitmapFactory.create(sdkMultiRangeLivenessResult.image),
        )
    }
}
