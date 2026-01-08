package com.innovatrics.dot.samples.documentautocapture

import com.innovatrics.dot.image.BitmapFactory
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

typealias SdkDocumentAutoCaptureResult = com.innovatrics.dot.document.autocapture.DocumentAutoCaptureResult

class CreateUiResultUseCase(
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default,
) {

    suspend operator fun invoke(sdkDocumentAutoCaptureResult: SdkDocumentAutoCaptureResult): DocumentAutoCaptureResult = withContext(context = defaultDispatcher) {
        DocumentAutoCaptureResult(
            bitmap = BitmapFactory.create(sdkDocumentAutoCaptureResult.image),
            documentAutoCaptureResult = sdkDocumentAutoCaptureResult,
        )
    }
}
