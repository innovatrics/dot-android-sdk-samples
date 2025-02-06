package com.innovatrics.dot.samples.nfcreading

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.innovatrics.dot.mrz.MachineReadableZone
import com.innovatrics.dot.nfc.reader.NfcTravelDocumentReaderResult
import com.innovatrics.dot.nfc.reader.ui.NfcTravelDocumentReaderFragment
import kotlinx.coroutines.launch

class NfcReadingViewModel(
    private val resolveAuthorityCertificatesFileUseCase: ResolveAuthorityCertificatesFileUseCase,
    private val createUiResultUseCase: CreateUiResultUseCase,
) : ViewModel() {

    private val mutableState: MutableLiveData<NfcReadingState> = MutableLiveData()
    val state: LiveData<NfcReadingState> = mutableState

    fun initializeState() {
        mutableState.value = NfcReadingState()
    }

    fun setupConfiguration(machineReadableZone: MachineReadableZone) {
        viewModelScope.launch {
            val configuration = NfcTravelDocumentReaderFragment.Configuration(
                password = createPassword(machineReadableZone),
                authorityCertificatesFilePath = resolveAuthorityCertificatesFileUseCase().path,
            )
            mutableState.value = state.value!!.copy(configuration = configuration)
        }
    }

    fun process(nfcTravelDocumentReaderResult: NfcTravelDocumentReaderResult) {
        viewModelScope.launch {
            val result = createUiResultUseCase(nfcTravelDocumentReaderResult)
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
