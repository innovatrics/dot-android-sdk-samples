package com.innovatrics.dot.samples.nfcreading

import com.innovatrics.dot.nfc.NfcKey

data class NfcReadingState(
    val isReading: Boolean = false,
    val nfcKey: NfcKey? = null,
    val result: NfcReadingResult? = null,
    val errorMessage: String? = null,
)
