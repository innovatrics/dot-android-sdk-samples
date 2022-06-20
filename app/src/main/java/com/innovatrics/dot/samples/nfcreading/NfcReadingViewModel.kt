package com.innovatrics.dot.samples.nfcreading

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.innovatrics.dot.document.mrz.MachineReadableZone
import kotlinx.coroutines.launch

class NfcReadingViewModel(
    private val readTravelDocumentUseCase: ReadTravelDocumentUseCase,
) : ViewModel() {

    private val mutableState: MutableLiveData<NfcReadingState> = MutableLiveData()
    val state: LiveData<NfcReadingState> = mutableState

    fun initializeState() {
        mutableState.value = NfcReadingState()
    }

    fun resolveAndSetNfcKey(machineReadableZone: MachineReadableZone) {
        mutableState.value = NfcReadingState(nfcKey = createNfcKey(machineReadableZone))
    }

    fun read(intent: Intent) {
        viewModelScope.launch {
            mutableState.value = state.value!!.copy(isReading = true)
            mutableState.value = readSafely(intent)
        }
    }

    private suspend fun readSafely(intent: Intent): NfcReadingState {
        return try {
            val result = readTravelDocumentUseCase(intent, state.value!!.nfcKey!!)
            state.value!!.copy(isReading = false, result = result)
        } catch (e: Exception) {
            state.value!!.copy(isReading = false, errorMessage = e.message)
        }
    }

    fun notifyErrorMessageShown() {
        mutableState.value = state.value!!.copy(errorMessage = null)
    }
}
