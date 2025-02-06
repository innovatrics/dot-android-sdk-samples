package com.innovatrics.dot.samples.nfcreading

import android.graphics.Bitmap
import com.innovatrics.dot.nfc.reader.NfcTravelDocumentReaderResult

data class NfcReadingResult(
    val nfcTravelDocumentReaderResult: NfcTravelDocumentReaderResult,
    val faceBitmap: Bitmap?,
)
