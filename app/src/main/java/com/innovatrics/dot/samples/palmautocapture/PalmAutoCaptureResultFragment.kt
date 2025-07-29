package com.innovatrics.dot.samples.palmautocapture

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.innovatrics.dot.samples.R
import com.innovatrics.dot.samples.ui.gson
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PalmAutoCaptureResultFragment : Fragment(R.layout.fragment_palm_auto_capture_result) {

    private val palmAutoCaptureViewModel: PalmAutoCaptureViewModel by activityViewModels()

    private lateinit var imageView: ImageView
    private lateinit var textView: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViews(view)
        setupPalmAutoCaptureViewModel()
    }

    private fun setViews(view: View) {
        imageView = view.findViewById(R.id.image)
        textView = view.findViewById(R.id.text)
    }

    private fun setupPalmAutoCaptureViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                palmAutoCaptureViewModel.state.collectLatest { state ->
                    showResult(result = state.result!!)
                }
            }
        }
    }

    private fun showResult(result: PalmAutoCaptureResult) {
        imageView.setImageBitmap(result.bitmap)
        textView.text = gson.toJson(result)
    }
}
