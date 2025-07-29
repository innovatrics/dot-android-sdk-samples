package com.innovatrics.dot.samples.faceautocapture

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

class FaceAutoCaptureResultFragment : Fragment(R.layout.fragment_face_auto_capture_result) {

    private val faceAutoCaptureViewModel: FaceAutoCaptureViewModel by activityViewModels()

    private lateinit var imageView: ImageView
    private lateinit var textView: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViews(view)
        setupFaceAutoCaptureViewModel()
    }

    private fun setViews(view: View) {
        imageView = view.findViewById(R.id.image)
        textView = view.findViewById(R.id.text)
    }

    private fun setupFaceAutoCaptureViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                faceAutoCaptureViewModel.state.collectLatest { state ->
                    showResult(result = state.result!!)
                }
            }
        }
    }

    private fun showResult(result: FaceAutoCaptureResult) {
        imageView.setImageBitmap(result.bitmap)
        textView.text = gson.toJson(result)
    }
}
