package com.innovatrics.dot.samples.nfcreading

import androidx.fragment.app.activityViewModels
import com.innovatrics.dot.nfc.reader.NfcTravelDocumentReaderResult
import com.innovatrics.dot.nfc.reader.ui.NfcTravelDocumentReaderFragment

class DefaultNfcTravelDocumentReaderFragment : NfcTravelDocumentReaderFragment() {

    private val nfcReadingViewModel: NfcReadingViewModel by activityViewModels()

    override fun provideConfiguration(): Configuration {
        return nfcReadingViewModel.state.value.configuration!!
    }

    override fun onSucceeded(result: NfcTravelDocumentReaderResult) {
        nfcReadingViewModel.process(result)
    }

    override fun onFailed(exception: Exception) {
        throw exception
    }
}
