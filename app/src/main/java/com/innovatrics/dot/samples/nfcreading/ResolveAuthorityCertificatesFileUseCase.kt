package com.innovatrics.dot.samples.nfcreading

import android.app.Application
import com.innovatrics.dot.samples.R
import com.innovatrics.dot.samples.io.ResourceCopier
import java.io.File
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ResolveAuthorityCertificatesFileUseCase(
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val application: Application,
    private val resourceCopier: ResourceCopier,
) {

    private val filename = "authority_certificates.pem"

    suspend operator fun invoke() = withContext(ioDispatcher) {
        resolveAuthorityCertificatesFile()
    }

    private fun resolveAuthorityCertificatesFile() = File(application.filesDir, filename).apply {
        writeAuthorityCertificatesToFile(this)
    }

    private fun writeAuthorityCertificatesToFile(file: File) = resourceCopier.copyToFile(R.raw.authority_certificates, file)
}
