package com.innovatrics.dot.samples.faceautocapture

import android.graphics.Bitmap

data class FaceAutoCaptureResult(
    val bitmap: Bitmap,
    val faceAutoCaptureResult: com.innovatrics.dot.face.autocapture.FaceAutoCaptureResult,
)
