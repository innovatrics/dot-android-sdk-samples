package com.innovatrics.dot.samples.nfcreading

import android.content.Intent
import android.nfc.NfcAdapter
import android.nfc.Tag
import com.innovatrics.dot.nfc.NfcKey
import com.innovatrics.dot.nfc.TravelDocument
import com.innovatrics.dot.samples.image.createBitmap
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ReadTravelDocumentUseCase(
    private val nfcTravelDocumentReaderFactory: NfcTravelDocumentReaderFactory,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) {

    private val nfcTravelDocumentReader by lazy { nfcTravelDocumentReaderFactory.create() }

    suspend operator fun invoke(intent: Intent, nfcKey: NfcKey): NfcReadingResult = withContext(ioDispatcher) {
        val travelDocument = readTravelDocument(intent, nfcKey)
        val faceBitmap = travelDocument.encodedIdentificationFeaturesFace.faceImage?.createBitmap()
        NfcReadingResult(travelDocument, faceBitmap)
    }

    private fun readTravelDocument(intent: Intent, nfcKey: NfcKey): TravelDocument {
        val tag = intent.getParcelableExtra<Tag>(NfcAdapter.EXTRA_TAG)!!
        return nfcTravelDocumentReader.read(tag, nfcKey)
    }
}
