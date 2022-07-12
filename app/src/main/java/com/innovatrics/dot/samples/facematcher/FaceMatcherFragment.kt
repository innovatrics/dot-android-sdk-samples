package com.innovatrics.dot.samples.facematcher

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.innovatrics.dot.face.similarity.FaceMatcher
import com.innovatrics.dot.samples.MainViewModel
import com.innovatrics.dot.samples.R
import com.innovatrics.dot.samples.face.DotFaceViewModel
import com.innovatrics.dot.samples.face.DotFaceViewModelFactory
import com.innovatrics.dot.samples.ui.createGson

class FaceMatcherFragment : Fragment(R.layout.fragment_face_matcher) {

    private val mainViewModel: MainViewModel by activityViewModels()
    private val gson = createGson()

    private lateinit var dotFaceViewModel: DotFaceViewModel
    private lateinit var faceMatcherViewModel: FaceMatcherViewModel
    private lateinit var sheldonImageView: ImageView
    private lateinit var leonardImageView: ImageView
    private lateinit var textView: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViews(view)
        setupDotFaceViewModel()
        setupFaceMatcherViewModel()
    }

    private fun setViews(view: View) {
        sheldonImageView = view.findViewById(R.id.sheldon)
        leonardImageView = view.findViewById(R.id.leonard)
        textView = view.findViewById(R.id.text)
    }

    private fun setupDotFaceViewModel() {
        val dotFaceViewModelFactory = DotFaceViewModelFactory(requireActivity().application)
        dotFaceViewModel = ViewModelProvider(this, dotFaceViewModelFactory)[DotFaceViewModel::class.java]
        dotFaceViewModel.state.observe(viewLifecycleOwner) { state ->
            if (state.isInitialized) {
                faceMatcherViewModel.matchFacesAsync()
            }
            state.errorMessage?.let {
                Snackbar.make(requireView(), it, Snackbar.LENGTH_SHORT).show()
                dotFaceViewModel.notifyErrorMessageShown()
            }
        }
        dotFaceViewModel.initializeDotFaceIfNeeded()
    }

    private fun setupFaceMatcherViewModel() {
        val faceMatcherViewModelFactory = FaceMatcherViewModelFactory(resources)
        faceMatcherViewModel = ViewModelProvider(this, faceMatcherViewModelFactory)[FaceMatcherViewModel::class.java]
        faceMatcherViewModel.state.observe(viewLifecycleOwner) { state ->
            mainViewModel.setProcessing(state.isProcessing)
            state.sheldonBitmap?.let(sheldonImageView::setImageBitmap)
            state.leonardBitmap?.let(leonardImageView::setImageBitmap)
            state.result?.let { showResult(it) }
            state.errorMessage?.let {
                Snackbar.make(requireView(), it, Snackbar.LENGTH_SHORT).show()
                faceMatcherViewModel.notifyErrorMessageShown()
            }
        }
        faceMatcherViewModel.loadBitmapsAsync()
    }

    private fun showResult(result: FaceMatcher.Result) {
        textView.text = gson.toJson(result)
    }
}
