package com.innovatrics.dot.samples.facematcher

import android.content.res.Resources
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.innovatrics.dot.face.similarity.FaceMatcherFactory

class FaceMatcherViewModelFactory(
    private val resources: Resources,
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val loadBitmapUseCase = LoadBitmapUseCase(resources)
        val faceMatcher = FaceMatcherFactory.create()
        val matchFacesUseCase = MatchFacesUseCase(faceMatcher)
        return FaceMatcherViewModel(loadBitmapUseCase, matchFacesUseCase) as T
    }
}
