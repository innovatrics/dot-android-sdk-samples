package com.innovatrics.dot.samples.palmautocapture

import com.innovatrics.dot.image.BitmapFactory
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

typealias SdkPalmAutoCaptureResult = com.innovatrics.dot.palm.autocapture.PalmAutoCaptureResult

class CreateUiResultUseCase(
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default,
) {

    suspend operator fun invoke(sdkPalmAutoCaptureResult: SdkPalmAutoCaptureResult): PalmAutoCaptureResult = withContext(context = defaultDispatcher) {
        PalmAutoCaptureResult(
            bitmap = BitmapFactory.create(sdkPalmAutoCaptureResult.image),
            palmAutoCaptureResult = sdkPalmAutoCaptureResult,
        )
    }
}
