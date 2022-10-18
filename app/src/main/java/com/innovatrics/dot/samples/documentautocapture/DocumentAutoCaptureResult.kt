package com.innovatrics.dot.samples.documentautocapture

import android.graphics.Bitmap

data class DocumentAutoCaptureResult(
    val bitmap: Bitmap,
    val documentAutoCaptureResult: com.innovatrics.dot.document.autocapture.DocumentAutoCaptureResult,
)
