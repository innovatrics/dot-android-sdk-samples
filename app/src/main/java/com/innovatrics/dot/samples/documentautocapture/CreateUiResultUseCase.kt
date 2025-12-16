package com.innovatrics.dot.samples.documentautocapture

import com.innovatrics.dot.image.BitmapFactory
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

typealias SdkDocumentAutoCaptureResult = com.innovatrics.dot.document.autocapture.DocumentAutoCaptureResult

class CreateUiResultUseCase(
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) {

    suspend operator fun invoke(sdkDocumentAutoCaptureResult: SdkDocumentAutoCaptureResult): DocumentAutoCaptureResult = withContext(ioDispatcher) {
        DocumentAutoCaptureResult(
            bitmap = BitmapFactory.create(sdkDocumentAutoCaptureResult.image),
            documentAutoCaptureResult = sdkDocumentAutoCaptureResult,
        )
    }
}
