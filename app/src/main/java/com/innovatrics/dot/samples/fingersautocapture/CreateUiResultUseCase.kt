package com.innovatrics.dot.samples.fingersautocapture

import com.innovatrics.dot.image.BitmapFactory
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

typealias SdkFingersAutoCaptureResult = com.innovatrics.dot.fingers.autocapture.FingersAutoCaptureResult

class CreateUiResultUseCase(
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default,
) {

    suspend operator fun invoke(sdkFingersAutoCaptureResult: SdkFingersAutoCaptureResult): FingersAutoCaptureResult = withContext(context = defaultDispatcher) {
        FingersAutoCaptureResult(
            fingerBitmaps = FingersAutoCaptureResult.FingerBitmaps(
                index = sdkFingersAutoCaptureResult.fingerBundles.index?.let { BitmapFactory.create(image = it.image) },
                middle = sdkFingersAutoCaptureResult.fingerBundles.middle?.let { BitmapFactory.create(image = it.image) },
                ring = sdkFingersAutoCaptureResult.fingerBundles.ring?.let { BitmapFactory.create(image = it.image) },
                little = sdkFingersAutoCaptureResult.fingerBundles.little?.let { BitmapFactory.create(image = it.image) },
            ),
            fingersAutoCaptureResult = sdkFingersAutoCaptureResult,
        )
    }
}
