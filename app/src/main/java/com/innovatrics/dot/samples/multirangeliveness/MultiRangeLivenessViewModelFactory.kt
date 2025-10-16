package com.innovatrics.dot.samples.multirangeliveness

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MultiRangeLivenessViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MultiRangeLivenessViewModel(CreateUiResultUseCase()) as T
    }
}
