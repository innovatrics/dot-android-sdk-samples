package com.innovatrics.dot.samples.documentautocapture

import com.innovatrics.dot.image.BitmapFactory
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CreateUiResultUseCase(
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) {

    suspend operator fun invoke(documentAutoCaptureResult: com.innovatrics.dot.document.autocapture.DocumentAutoCaptureResult): DocumentAutoCaptureResult = withContext(ioDispatcher) {
        DocumentAutoCaptureResult(
            bitmap = BitmapFactory.create(documentAutoCaptureResult.bgraRawImage),
            documentAutoCaptureResult = documentAutoCaptureResult,
        )
    }
}
