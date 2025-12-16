package com.innovatrics.dot.samples.faceautocapture

import com.innovatrics.dot.image.BitmapFactory
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

typealias SdkFaceAutoCaptureResult = com.innovatrics.dot.face.autocapture.FaceAutoCaptureResult

class CreateUiResultUseCase(
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) {

    suspend operator fun invoke(sdkFaceAutoCaptureResult: SdkFaceAutoCaptureResult): FaceAutoCaptureResult = withContext(ioDispatcher) {
        FaceAutoCaptureResult(
            bitmap = BitmapFactory.create(sdkFaceAutoCaptureResult.image),
            faceAutoCaptureResult = sdkFaceAutoCaptureResult,
        )
    }
}
