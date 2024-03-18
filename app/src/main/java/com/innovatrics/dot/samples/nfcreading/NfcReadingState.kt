package com.innovatrics.dot.samples.nfcreading

import com.innovatrics.dot.nfc.reader.ui.NfcTravelDocumentReaderFragment

data class NfcReadingState(
    val configuration: NfcTravelDocumentReaderFragment.Configuration? = null,
    val result: NfcReadingResult? = null,
    val errorMessage: String? = null,
)
