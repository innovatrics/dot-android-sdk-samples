package com.innovatrics.dot.samples.palmautocapture

import android.graphics.Bitmap
import com.innovatrics.dot.palm.autocapture.PalmAutoCaptureResult

data class PalmAutoCaptureResult(
    val bitmap: Bitmap,
    val palmAutoCaptureResult: PalmAutoCaptureResult,
)
