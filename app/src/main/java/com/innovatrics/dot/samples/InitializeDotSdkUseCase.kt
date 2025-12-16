package com.innovatrics.dot.samples

import android.content.Context
import android.content.res.Resources
import com.innovatrics.dot.core.DotSdk
import com.innovatrics.dot.core.library.DotDocumentLibraryConfiguration
import com.innovatrics.dot.core.library.DotFaceLibraryConfiguration
import com.innovatrics.dot.core.library.DotNfcLibraryConfiguration
import com.innovatrics.dot.core.library.DotPalmLibraryConfiguration
import com.innovatrics.dot.core.library.Libraries
import com.innovatrics.dot.core.library.face.DotFaceDetectionModuleConfiguration
import com.innovatrics.dot.core.library.face.DotFaceExpressionNeutralModuleConfiguration
import java.io.InputStream
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class InitializeDotSdkUseCase(
    private val dotSdk: DotSdk = DotSdk,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) {

    suspend operator fun invoke(context: Context) = withContext(dispatcher) {
        val configuration = createDotSdkConfiguration(context)
        dotSdk.initialize(configuration)
    }

    private fun createDotSdkConfiguration(context: Context) = DotSdk.Configuration(
        context = context,
        licenseBytes = readLicenseBytes(context.resources),
        libraries = Libraries(
            document = DotDocumentLibraryConfiguration,
            face = DotFaceLibraryConfiguration(
                modules = DotFaceLibraryConfiguration.Modules(
                    detection = DotFaceDetectionModuleConfiguration.Fast,
                    expressionNeutral = DotFaceExpressionNeutralModuleConfiguration,
                ),
            ),
            nfc = DotNfcLibraryConfiguration,
            palm = DotPalmLibraryConfiguration(),
        ),
    )

    private fun readLicenseBytes(resources: Resources) = resources.openRawResource(R.raw.dot_license).use(InputStream::readBytes)
}
