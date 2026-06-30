package com.innovatrics.dot.samples.fingersautocapture

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class FingersAutoCaptureViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FingersAutoCaptureViewModel(
            createUiResultUseCase = CreateUiResultUseCase(),
            transformFingersToFingerprints = TransformFingersToFingerprintsUseCase(),
        ) as T
    }
}
