package com.innovatrics.dot.samples.multirangeliveness

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.innovatrics.dot.samples.R
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MultiRangeLivenessResultFragment : Fragment(R.layout.fragment_multirange_liveness_result) {

    private val multiRangeLivenessViewModel: MultiRangeLivenessViewModel by activityViewModels()

    private lateinit var imageView: ImageView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViews(view)
        setupMultiRangeLivenessViewModel()
    }

    private fun setViews(view: View) {
        imageView = view.findViewById(R.id.image)
    }

    private fun setupMultiRangeLivenessViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                multiRangeLivenessViewModel.state.collectLatest { state ->
                    showResult(result = state.result!!)
                }
            }
        }
    }

    private fun showResult(result: MultiRangeLivenessResult) {
        imageView.setImageBitmap(result.bitmap)
    }
}
