package com.innovatrics.dot.samples.nfcreading

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.innovatrics.dot.samples.io.RawResourceCopier

class NfcReadingViewModelFactory(
    private val application: Application,
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>) = NfcReadingViewModel(
        resolveAuthorityCertificatesFileUseCase = ResolveAuthorityCertificatesFileUseCase(
            application = application,
            resourceCopier = RawResourceCopier(application.resources),
        ),
        createNfcReadingResultUseCase = CreateNfcReadingResultUseCase(),
    ) as T
}
