package com.innovatrics.dot.samples

import android.content.Intent

data class MainState(
    val isProcessing: Boolean = false,
    val intent: Intent? = null,
    val errorMessage: String? = null,
)
