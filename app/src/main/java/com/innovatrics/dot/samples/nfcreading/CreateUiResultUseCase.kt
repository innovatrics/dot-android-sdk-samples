package com.innovatrics.dot.samples.nfcreading

import com.innovatrics.dot.nfc.reader.NfcTravelDocumentReaderResult
import com.innovatrics.dot.samples.image.createBitmap
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CreateUiResultUseCase(
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) {

    suspend operator fun invoke(nfcTravelDocumentReaderResult: NfcTravelDocumentReaderResult): NfcReadingResult = withContext(ioDispatcher) {
        NfcReadingResult(
            nfcTravelDocumentReaderResult = nfcTravelDocumentReaderResult,
            faceBitmap = nfcTravelDocumentReaderResult.travelDocument.encodedIdentificationFeaturesFace.faceImage?.createBitmap(),
        )
    }
}
