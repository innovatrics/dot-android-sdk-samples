package com.innovatrics.dot.samples.faceautocapture

data class FaceAutoCaptureState(
    val isProcessing: Boolean = false,
    val result: FaceAutoCaptureResult? = null,
    val errorMessage: String? = null,
)
