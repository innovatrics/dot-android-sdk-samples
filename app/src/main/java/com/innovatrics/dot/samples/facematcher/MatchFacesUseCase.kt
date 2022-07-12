package com.innovatrics.dot.samples.facematcher

import android.graphics.Bitmap
import com.innovatrics.dot.face.image.BgrRawImageFactory
import com.innovatrics.dot.face.image.FaceImage
import com.innovatrics.dot.face.image.FaceImageFactory
import com.innovatrics.dot.face.similarity.FaceMatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class MatchFacesUseCase(
    private val faceMatcher: FaceMatcher,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) {

    companion object {
        const val MIN_FACE_SIZE_RATIO = 0.10
        const val MAX_FACE_SIZE_RATIO = 0.25
    }

    suspend operator fun invoke(sheldonBitmap: Bitmap, leonardBitmap: Bitmap): FaceMatcher.Result = withContext(ioDispatcher) {
        val sheldonFaceImageDeferred = async { createFaceImage(sheldonBitmap) }
        val leonardFaceImageDeferred = async { createFaceImage(leonardBitmap) }
        faceMatcher.match(sheldonFaceImageDeferred.await(), leonardFaceImageDeferred.await())
    }

    private fun createFaceImage(bitmap: Bitmap): FaceImage {
        val bgrRawImage = BgrRawImageFactory.create(bitmap)
        return FaceImageFactory.create(bgrRawImage, MIN_FACE_SIZE_RATIO, MAX_FACE_SIZE_RATIO)
    }
}
