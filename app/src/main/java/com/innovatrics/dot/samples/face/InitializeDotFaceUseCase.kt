package com.innovatrics.dot.samples.face

import android.content.Context
import android.content.res.Resources
import com.innovatrics.dot.face.DotFaceLibrary
import com.innovatrics.dot.face.DotFaceLibraryConfiguration
import com.innovatrics.dot.face.detection.fast.DotFaceDetectionFastModule
import com.innovatrics.dot.face.expressionneutral.DotFaceExpressionNeutralModule
import com.innovatrics.dot.face.modules.DotFaceModule
import com.innovatrics.dot.face.passiveliveness.DotFacePassiveLivenessModule
import com.innovatrics.dot.face.verification.DotFaceVerificationModule
import com.innovatrics.dot.samples.R
import java.io.InputStream
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class InitializeDotFaceUseCase(
    private val dotFaceLibrary: DotFaceLibrary = DotFaceLibrary.getInstance(),
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) {

    suspend operator fun invoke(context: Context, listener: DotFaceLibrary.InitializationListener) = withContext(ioDispatcher) {
        val license = readLicense(context.resources)
        val modules = createModules()
        val configuration = DotFaceLibraryConfiguration.Builder(context, license, modules).build()
        dotFaceLibrary.initializeAsync(configuration, listener)
    }

    private fun readLicense(resources: Resources): ByteArray {
        return resources.openRawResource(R.raw.dot_face_license).use(InputStream::readBytes)
    }

    private fun createModules(): List<DotFaceModule> {
        return listOf(
            DotFaceDetectionFastModule.of(),
            DotFaceVerificationModule.of(),
            DotFaceExpressionNeutralModule.of(),
            DotFacePassiveLivenessModule.of(),
        )
    }
}
