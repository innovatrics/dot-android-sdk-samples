package com.innovatrics.dot.samples.faceautocapture

import com.innovatrics.dot.image.BitmapFactory
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CreateUiResultUseCase(
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) {

    suspend operator fun invoke(faceAutoCaptureResult: com.innovatrics.dot.face.autocapture.FaceAutoCaptureResult): FaceAutoCaptureResult = withContext(ioDispatcher) {
        FaceAutoCaptureResult(
            bitmap = BitmapFactory.create(faceAutoCaptureResult.bgrRawImage),
            confidence = faceAutoCaptureResult.detectedFace?.getConfidence(),
            faceAspects = faceAutoCaptureResult.detectedFace?.evaluateFaceAspects(),
            faceQuality = faceAutoCaptureResult.detectedFace?.evaluateFaceQuality(),
            passiveLivenessFaceAttribute = faceAutoCaptureResult.detectedFace?.evaluatePassiveLiveness(),
        )
    }
}
