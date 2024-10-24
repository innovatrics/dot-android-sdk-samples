package com.innovatrics.dot.samples.palmautocapture

data class PalmAutoCaptureState(
    val isProcessing: Boolean = false,
    val result: PalmAutoCaptureResult? = null,
    val errorMessage: String? = null,
)
