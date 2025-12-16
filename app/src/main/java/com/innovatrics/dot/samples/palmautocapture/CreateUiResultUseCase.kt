package com.innovatrics.dot.samples.palmautocapture

import com.innovatrics.dot.image.BitmapFactory
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

typealias SdkPalmAutoCaptureResult = com.innovatrics.dot.palm.autocapture.PalmAutoCaptureResult

class CreateUiResultUseCase(
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) {

    suspend operator fun invoke(sdkPalmAutoCaptureResult: SdkPalmAutoCaptureResult): PalmAutoCaptureResult = withContext(ioDispatcher) {
        PalmAutoCaptureResult(
            bitmap = BitmapFactory.create(sdkPalmAutoCaptureResult.image),
            palmAutoCaptureResult = sdkPalmAutoCaptureResult,
        )
    }
}
