package com.innovatrics.dot.samples.face

import android.content.Context
import com.innovatrics.android.commons.io.RawResourceReader
import com.innovatrics.android.commons.io.ResourceReader
import com.innovatrics.dot.face.DotFace
import com.innovatrics.dot.face.DotFaceConfiguration
import com.innovatrics.dot.face.detection.fast.DotFaceDetectionFastModule
import com.innovatrics.dot.face.expressionneutral.DotFaceExpressionNeutralModule
import com.innovatrics.dot.face.modules.DotFaceModule
import com.innovatrics.dot.face.passiveliveness.DotFacePassiveLivenessModule
import com.innovatrics.dot.samples.R
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class InitializeDotFaceUseCase(
    private val dotFace: DotFace = DotFace.getInstance(),
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) {

    suspend operator fun invoke(context: Context, listener: DotFace.InitializationListener) = withContext(ioDispatcher) {
        val license = readLicense(context)
        val modules = createModules()
        val configuration = DotFaceConfiguration.Builder(context, license, modules).build()
        dotFace.initializeAsync(configuration, listener)
    }

    private fun readLicense(context: Context): ByteArray {
        val resourceReader: ResourceReader = RawResourceReader(context.resources)
        return resourceReader.read(R.raw.dot_face_license)
    }

    private fun createModules(): List<DotFaceModule> {
        return listOf(
            DotFaceDetectionFastModule.of(),
            DotFaceExpressionNeutralModule.of(),
            DotFacePassiveLivenessModule.of(),
        )
    }
}
