package com.innovatrics.dot.samples.fingersautocapture

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.innovatrics.dot.samples.R
import com.innovatrics.dot.samples.ui.gson
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FingersAutoCaptureResultFragment : Fragment(R.layout.fragment_fingers_auto_capture_result) {

    private val fingersAutoCaptureViewModel: FingersAutoCaptureViewModel by activityViewModels()

    private lateinit var indexFingerImageView: ImageView
    private lateinit var middleFingerImageView: ImageView
    private lateinit var ringFingerImageView: ImageView
    private lateinit var littleFingerImageView: ImageView
    private lateinit var fingerprintImagesProgressIndicator: CircularProgressIndicator
    private lateinit var fingerprintImagesContentView: View
    private lateinit var indexFingerprintImageView: ImageView
    private lateinit var middleFingerprintImageView: ImageView
    private lateinit var ringFingerprintImageView: ImageView
    private lateinit var littleFingerprintImageView: ImageView
    private lateinit var textView: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViews(view)
        setupFingersAutoCaptureViewModel()
    }

    private fun setViews(view: View) {
        indexFingerImageView = view.findViewById(R.id.index_finger_image)
        middleFingerImageView = view.findViewById(R.id.middle_finger_image)
        ringFingerImageView = view.findViewById(R.id.ring_finger_image)
        littleFingerImageView = view.findViewById(R.id.little_finger_image)
        fingerprintImagesProgressIndicator = view.findViewById(R.id.fingerprint_images_progress_indicator)
        fingerprintImagesContentView = view.findViewById(R.id.fingerprint_images_content)
        indexFingerprintImageView = view.findViewById(R.id.index_fingerprint_image)
        middleFingerprintImageView = view.findViewById(R.id.middle_fingerprint_image)
        ringFingerprintImageView = view.findViewById(R.id.ring_fingerprint_image)
        littleFingerprintImageView = view.findViewById(R.id.little_fingerprint_image)
        textView = view.findViewById(R.id.text)
    }

    private fun setupFingersAutoCaptureViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
                fingersAutoCaptureViewModel.state.collectLatest { state ->
                    showResult(result = state.result!!)
                }
            }
        }
    }

    private fun showResult(result: FingersAutoCaptureResult) {
        indexFingerImageView.setImageBitmap(result.fingerBitmaps.index)
        middleFingerImageView.setImageBitmap(result.fingerBitmaps.middle)
        ringFingerImageView.setImageBitmap(result.fingerBitmaps.ring)
        littleFingerImageView.setImageBitmap(result.fingerBitmaps.little)
        showFingerprints(result.fingerprintBitmaps)
        textView.text = gson.toJson(result)
    }

    private fun showFingerprints(fingerprintBitmaps: FingersAutoCaptureResult.FingerprintBitmaps?) {
        fingerprintBitmaps?.let {
            fingerprintImagesProgressIndicator.visibility = View.GONE
            fingerprintImagesContentView.visibility = View.VISIBLE
            indexFingerprintImageView.setImageBitmap(it.index)
            middleFingerprintImageView.setImageBitmap(it.middle)
            ringFingerprintImageView.setImageBitmap(it.ring)
            littleFingerprintImageView.setImageBitmap(it.little)
        } ?: run {
            fingerprintImagesProgressIndicator.visibility = View.VISIBLE
            fingerprintImagesContentView.visibility = View.GONE
        }
    }
}
