package com.innovatrics.dot.samples.smileliveness

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.innovatrics.dot.samples.R

class SmileLivenessResultFragment : Fragment(R.layout.fragment_smile_liveness_result) {

    private val smileLivenessViewModel: SmileLivenessViewModel by activityViewModels()

    private lateinit var imageView: ImageView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViews(view)
        setupSmileLivenessViewModel()
    }

    private fun setViews(view: View) {
        imageView = view.findViewById(R.id.image)
    }

    private fun setupSmileLivenessViewModel() {
        smileLivenessViewModel.state.observe(viewLifecycleOwner) { showResult(it.result!!) }
    }

    private fun showResult(result: SmileLivenessResult) {
        imageView.setImageBitmap(result.bitmap)
    }
}
