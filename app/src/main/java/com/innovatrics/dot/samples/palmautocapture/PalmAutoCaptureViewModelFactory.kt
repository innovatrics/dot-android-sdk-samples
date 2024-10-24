package com.innovatrics.dot.samples.palmautocapture

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PalmAutoCaptureViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PalmAutoCaptureViewModel(CreateUiResultUseCase()) as T
    }
}
