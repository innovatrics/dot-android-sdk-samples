package com.innovatrics.dot.samples.face

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.innovatrics.dot.face.DotFace
import kotlinx.coroutines.launch

class DotFaceViewModel(
    application: Application,
    private val initializeDotFaceUseCase: InitializeDotFaceUseCase,
) : AndroidViewModel(application) {

    private val mutableState: MutableLiveData<DotFaceState> = MutableLiveData<DotFaceState>().apply {
        value = DotFaceState()
    }
    val state: LiveData<DotFaceState> = mutableState

    fun initializeDotFaceIfNeeded() {
        viewModelScope.launch {
            initializeDotFaceIfNeededInternal()
        }
    }

    private suspend fun initializeDotFaceIfNeededInternal() {
        when (DotFace.getInstance().isInitialized) {
            true -> mutableState.value = state.value!!.copy(isInitialized = true)
            false -> initializeDotFace()
        }
    }

    private suspend fun initializeDotFace() {
        val initializationListener = createDotFaceInitializationListener()
        initializeDotFaceUseCase(getApplication(), initializationListener)
    }

    private fun createDotFaceInitializationListener(): DotFace.InitializationListener {
        return DotFace.InitializationListener { result ->
            val newState = resolveStateFromResult(result)
            mutableState.postValue(newState)
        }
    }

    private fun resolveStateFromResult(result: DotFace.Result): DotFaceState {
        return when (result.code) {
            DotFace.Result.Code.OK -> state.value!!.copy(isInitialized = true)
            else -> state.value!!.copy(isInitialized = false, errorMessage = result.exception.message)
        }
    }

    fun notifyErrorMessageShown() {
        mutableState.value = state.value!!.copy(errorMessage = null)
    }
}
