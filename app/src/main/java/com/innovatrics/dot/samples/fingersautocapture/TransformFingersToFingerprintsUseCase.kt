package com.innovatrics.dot.samples.fingersautocapture

import com.innovatrics.dot.fingers.autocapture.FingerBundles
import com.innovatrics.dot.fingers.transformation.FingerToFingerprintTransformer
import com.innovatrics.dot.fingers.transformation.FingerToFingerprintTransformerFactory
import com.innovatrics.dot.samples.image.toBitmap
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TransformFingersToFingerprintsUseCase(
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default,
) {

    private val fingerToFingerprintTransformer: FingerToFingerprintTransformer by lazy { FingerToFingerprintTransformerFactory.create() }

    suspend operator fun invoke(fingerBundles: FingerBundles): FingersAutoCaptureResult.FingerprintBitmaps = withContext(context = defaultDispatcher) {
        val fingerprintImages = fingerToFingerprintTransformer.transform(fingerBundles)
        FingersAutoCaptureResult.FingerprintBitmaps(
            index = fingerprintImages.index?.toBitmap(),
            middle = fingerprintImages.middle?.toBitmap(),
            ring = fingerprintImages.ring?.toBitmap(),
            little = fingerprintImages.little?.toBitmap(),
        )
    }
}
