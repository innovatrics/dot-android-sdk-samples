package com.innovatrics.dot.samples.nfcreading

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.innovatrics.dot.document.mrz.MachineReadableZone
import com.innovatrics.dot.nfc.TravelDocument
import com.innovatrics.dot.nfc.reader.ui.NfcTravelDocumentReaderFragment
import kotlinx.coroutines.launch

class NfcReadingViewModel(
    private val resolveAuthorityCertificatesFileUseCase: ResolveAuthorityCertificatesFileUseCase,
    private val createNfcReadingResultUseCase: CreateNfcReadingResultUseCase,
) : ViewModel() {

    private val mutableState: MutableLiveData<NfcReadingState> = MutableLiveData()
    val state: LiveData<NfcReadingState> = mutableState

    fun initializeState() {
        mutableState.value = NfcReadingState()
    }

    fun setupConfiguration(machineReadableZone: MachineReadableZone) {
        viewModelScope.launch {
            val configuration = NfcTravelDocumentReaderFragment.Configuration(
                nfcKey = createNfcKey(machineReadableZone),
                authorityCertificatesFilePath = resolveAuthorityCertificatesFileUseCase().path,
            )
            mutableState.value = state.value!!.copy(configuration = configuration)
        }
    }

    fun setTravelDocument(travelDocument: TravelDocument) {
        viewModelScope.launch {
            val result = createNfcReadingResultUseCase(travelDocument)
            mutableState.value = state.value!!.copy(result = result)
        }
    }

    fun setError(exception: Exception) {
        viewModelScope.launch {
            mutableState.value = state.value!!.copy(errorMessage = exception.message ?: exception::class.java.name)
        }
    }

    fun notifyErrorMessageShown() {
        mutableState.value = state.value!!.copy(errorMessage = null)
    }
}
