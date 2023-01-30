package com.innovatrics.dot.samples.faceautocapture

import com.innovatrics.dot.face.detection.DetectedFace
import com.innovatrics.dot.face.image.BitmapFactory
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProcessDetectedFaceUseCase(
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) {

    suspend operator fun invoke(detectedFace: DetectedFace): FaceAutoCaptureResult = withContext(ioDispatcher) {
        FaceAutoCaptureResult(
            bitmap = BitmapFactory.create(detectedFace.image),
            confidence = detectedFace.getConfidence(),
            faceAspects = detectedFace.evaluateFaceAspects(),
            faceQuality = detectedFace.evaluateFaceQuality(),
            passiveLivenessFaceAttribute = detectedFace.evaluatePassiveLiveness(),
        )
    }
}
