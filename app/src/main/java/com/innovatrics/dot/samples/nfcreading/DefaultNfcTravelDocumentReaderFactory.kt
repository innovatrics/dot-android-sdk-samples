package com.innovatrics.dot.samples.nfcreading

import android.content.res.Resources
import com.innovatrics.dot.nfc.NfcTravelDocumentReader
import com.innovatrics.dot.nfc.NfcTravelDocumentReaderConfiguration
import com.innovatrics.dot.nfc.security.CertificatesFactory
import com.innovatrics.dot.samples.R
import java.security.cert.X509Certificate

class DefaultNfcTravelDocumentReaderFactory(
    private val resources: Resources,
) : NfcTravelDocumentReaderFactory {

    override fun create(): NfcTravelDocumentReader {
        val authorityCertificates = createAuthorityCertificates(resources)
        val configuration = NfcTravelDocumentReaderConfiguration.Builder()
            .authorityCertificates(authorityCertificates)
            .build()
        return com.innovatrics.dot.nfc.NfcTravelDocumentReaderFactory.create(configuration)
    }

    fun createAuthorityCertificates(resources: Resources): Set<X509Certificate> {
        resources.openRawResource(R.raw.master_list).use { inputStream ->
            return CertificatesFactory.create(inputStream)
        }
    }
}
