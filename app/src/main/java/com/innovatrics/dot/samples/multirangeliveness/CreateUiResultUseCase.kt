package com.innovatrics.dot.samples.multirangeliveness

import com.innovatrics.dot.image.BitmapFactory
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

typealias SdkMultiRangeLivenessResult = com.innovatrics.dot.face.liveness.multirange.MultiRangeLivenessResult

class CreateUiResultUseCase(
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) {

    suspend operator fun invoke(sdkMultiRangeLivenessResult: SdkMultiRangeLivenessResult): MultiRangeLivenessResult = withContext(ioDispatcher) {
        MultiRangeLivenessResult(
            bitmap = BitmapFactory.create(sdkMultiRangeLivenessResult.image),
        )
    }
}
