package com.innovatrics.dot.samples.facematcher

import android.graphics.Bitmap
import com.innovatrics.dot.face.similarity.FaceMatcher

data class FaceMatcherState(
    val isProcessing: Boolean = false,
    val sheldonBitmap: Bitmap? = null,
    val leonardBitmap: Bitmap? = null,
    val result: FaceMatcher.Result? = null,
    val errorMessage: String? = null,
)
