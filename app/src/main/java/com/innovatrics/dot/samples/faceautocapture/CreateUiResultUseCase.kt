package com.innovatrics.dot.samples.faceautocapture

import com.innovatrics.dot.image.BitmapFactory
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

typealias SdkFaceAutoCaptureResult = com.innovatrics.dot.face.autocapture.FaceAutoCaptureResult

class CreateUiResultUseCase(
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default,
) {

    suspend operator fun invoke(sdkFaceAutoCaptureResult: SdkFaceAutoCaptureResult): FaceAutoCaptureResult = withContext(context = defaultDispatcher) {
        FaceAutoCaptureResult(
            bitmap = BitmapFactory.create(sdkFaceAutoCaptureResult.image),
            faceAutoCaptureResult = sdkFaceAutoCaptureResult,
        )
    }
}
