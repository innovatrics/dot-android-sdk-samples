package com.innovatrics.dot.samples.nfcreading

import android.content.res.Resources
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class NfcReadingViewModelFactory(
    private val resources: Resources,
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val nfcTravelDocumentReader = createNfcTravelDocumentReader(resources)
        val readTravelDocumentUseCase = ReadTravelDocumentUseCase(nfcTravelDocumentReader)
        return NfcReadingViewModel(readTravelDocumentUseCase) as T
    }
}
