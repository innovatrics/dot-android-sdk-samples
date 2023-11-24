package com.innovatrics.dot.samples.faceautocapture

import android.graphics.Bitmap
import com.innovatrics.dot.face.quality.FaceAspects
import com.innovatrics.dot.face.quality.FaceQuality

data class FaceAutoCaptureResult(
    val bitmap: Bitmap,
    val confidence: Double?,
    val faceAspects: FaceAspects?,
    val faceQuality: FaceQuality?,
)
