package com.innovatrics.dot.samples.nfcreading

import com.innovatrics.dot.nfc.NfcTravelDocumentReader

interface NfcTravelDocumentReaderFactory {

    fun create(): NfcTravelDocumentReader
}
