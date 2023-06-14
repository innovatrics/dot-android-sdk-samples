package com.innovatrics.dot.samples.smileliveness

import com.innovatrics.dot.image.BitmapFactory
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CreateUiResultUseCase(
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) {

    suspend operator fun invoke(smileLivenessResult: com.innovatrics.dot.face.liveness.smile.SmileLivenessResult): SmileLivenessResult = withContext(ioDispatcher) {
        SmileLivenessResult(
            bitmap = BitmapFactory.create(smileLivenessResult.bgrRawImage),
        )
    }
}
