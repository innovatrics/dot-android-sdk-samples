package com.innovatrics.dot.samples.nfcreading

import android.graphics.Bitmap
import com.innovatrics.dot.nfc.TravelDocument

data class NfcReadingResult(
    val travelDocument: TravelDocument,
    val faceBitmap: Bitmap? = null,
)
