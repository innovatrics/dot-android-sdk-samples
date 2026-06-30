package com.innovatrics.dot.samples.fingersautocapture

import android.graphics.Bitmap

data class FingersAutoCaptureResult(
    val fingerBitmaps: FingerBitmaps,
    val fingerprintBitmaps: FingerprintBitmaps? = null,
    val fingersAutoCaptureResult: com.innovatrics.dot.fingers.autocapture.FingersAutoCaptureResult,
) {

    data class FingerBitmaps(
        val index: Bitmap?,
        val middle: Bitmap?,
        val ring: Bitmap?,
        val little: Bitmap?,
    )

    data class FingerprintBitmaps(
        val index: Bitmap?,
        val middle: Bitmap?,
        val ring: Bitmap?,
        val little: Bitmap?,
    )
}
