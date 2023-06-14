package com.innovatrics.dot.samples.smileliveness

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SmileLivenessViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SmileLivenessViewModel(CreateUiResultUseCase()) as T
    }
}
