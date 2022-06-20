package com.innovatrics.dot.samples.documentautocapture

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.innovatrics.dot.document.autocapture.DocumentAutoCaptureResult
import com.innovatrics.dot.image.BitmapFactory

class DocumentAutoCaptureViewModel : ViewModel() {

    private val mutableState: MutableLiveData<DocumentAutoCaptureState> = MutableLiveData()
    val state: LiveData<DocumentAutoCaptureState> = mutableState

    fun initializeState() {
        mutableState.value = DocumentAutoCaptureState()
    }

    fun process(documentAutoCaptureResult: DocumentAutoCaptureResult) {
        val uiResult = createUiResult(documentAutoCaptureResult)
        mutableState.value = state.value!!.copy(result = uiResult)
    }

    private fun createUiResult(documentAutoCaptureResult: DocumentAutoCaptureResult): com.innovatrics.dot.samples.documentautocapture.DocumentAutoCaptureResult {
        return DocumentAutoCaptureResult(
            bitmap = BitmapFactory.create(documentAutoCaptureResult.bgraRawImage),
            documentDetectorResult = documentAutoCaptureResult.documentDetectorResult
        )
    }
}
