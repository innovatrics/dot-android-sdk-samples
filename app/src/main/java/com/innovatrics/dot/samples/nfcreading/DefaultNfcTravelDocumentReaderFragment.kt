package com.innovatrics.dot.samples.nfcreading

import androidx.fragment.app.activityViewModels
import com.innovatrics.dot.nfc.TravelDocument
import com.innovatrics.dot.nfc.reader.ui.NfcTravelDocumentReaderFragment

class DefaultNfcTravelDocumentReaderFragment : NfcTravelDocumentReaderFragment() {

    private val nfcReadingViewModel: NfcReadingViewModel by activityViewModels()

    override fun onSucceeded(travelDocument: TravelDocument) {
        nfcReadingViewModel.setTravelDocument(travelDocument)
    }

    override fun onFailed(exception: Exception) {
        nfcReadingViewModel.setError(exception)
    }
}
