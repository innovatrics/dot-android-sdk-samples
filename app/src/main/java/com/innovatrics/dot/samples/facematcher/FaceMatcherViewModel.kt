package com.innovatrics.dot.samples.facematcher

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.innovatrics.dot.samples.R
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class FaceMatcherViewModel(
    private val loadBitmapUseCase: LoadBitmapUseCase,
    private val matchFacesUseCase: MatchFacesUseCase,
) : ViewModel() {

    private val mutableState: MutableLiveData<FaceMatcherState> = MutableLiveData<FaceMatcherState>().apply { value = FaceMatcherState(isProcessing = true) }
    val state: LiveData<FaceMatcherState> = mutableState
    private lateinit var loadBitmapsJob: Job

    fun loadBitmapsAsync() {
        loadBitmapsJob = viewModelScope.launch { loadBitmaps() }
    }

    private suspend fun loadBitmaps() = coroutineScope {
        launch { loadSheldonBitmapSafely() }
        launch { loadLeonardBitmapSafely() }
    }

    private suspend fun loadSheldonBitmapSafely() {
        mutableState.value = try {
            val bitmap = loadBitmapUseCase(R.drawable.sheldon)
            state.value!!.copy(sheldonBitmap = bitmap)
        } catch (e: Exception) {
            state.value!!.copy(errorMessage = e.message)
        }
    }

    private suspend fun loadLeonardBitmapSafely() {
        mutableState.value = try {
            val bitmap = loadBitmapUseCase(R.drawable.leonard)
            state.value!!.copy(leonardBitmap = bitmap)
        } catch (e: Exception) {
            state.value!!.copy(errorMessage = e.message)
        }
    }

    fun matchFacesAsync() {
        viewModelScope.launch {
            loadBitmapsJob.join()
            matchFacesSafely()
        }
    }

    private suspend fun matchFacesSafely() {
        mutableState.value = try {
            val result = matchFacesUseCase(state.value!!.sheldonBitmap!!, state.value!!.leonardBitmap!!)
            state.value!!.copy(isProcessing = false, result = result)
        } catch (e: Exception) {
            state.value!!.copy(isProcessing = false, errorMessage = e.message)
        }
    }

    fun notifyErrorMessageShown() {
        mutableState.value = state.value!!.copy(errorMessage = null)
    }
}
