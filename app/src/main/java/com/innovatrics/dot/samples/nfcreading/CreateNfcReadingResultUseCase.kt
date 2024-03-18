package com.innovatrics.dot.samples.nfcreading

import com.innovatrics.dot.nfc.TravelDocument
import com.innovatrics.dot.samples.image.createBitmap
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CreateNfcReadingResultUseCase(
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) {

    suspend operator fun invoke(travelDocument: TravelDocument): NfcReadingResult = withContext(ioDispatcher) {
        val faceBitmap = travelDocument.encodedIdentificationFeaturesFace.faceImage?.createBitmap()
        NfcReadingResult(travelDocument, faceBitmap)
    }
}
