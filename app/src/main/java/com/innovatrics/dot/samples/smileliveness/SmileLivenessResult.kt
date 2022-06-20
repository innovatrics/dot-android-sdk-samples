package com.innovatrics.dot.samples.smileliveness

import android.graphics.Bitmap

data class SmileLivenessResult(
    val neutralExpressionBitmap: Bitmap,
    val smileExpressionBitmap: Bitmap,
)
