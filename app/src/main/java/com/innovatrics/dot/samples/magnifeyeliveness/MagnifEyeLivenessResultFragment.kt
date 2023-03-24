package com.innovatrics.dot.samples.magnifeyeliveness

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.innovatrics.dot.samples.R

class MagnifEyeLivenessResultFragment : Fragment(R.layout.fragment_magnifeye_liveness_result) {

    private val magnifEyeLivenessViewModel: MagnifEyeLivenessViewModel by activityViewModels()

    private lateinit var imageView: ImageView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViews(view)
        setupMagnifEyeLivenessViewModel()
    }

    private fun setViews(view: View) {
        imageView = view.findViewById(R.id.image)
    }

    private fun setupMagnifEyeLivenessViewModel() {
        magnifEyeLivenessViewModel.state.observe(viewLifecycleOwner) { showResult(it.result!!) }
    }

    private fun showResult(result: MagnifEyeLivenessResult) {
        imageView.setImageBitmap(result.bitmap)
    }
}
