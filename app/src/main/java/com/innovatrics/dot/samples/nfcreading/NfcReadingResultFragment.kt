package com.innovatrics.dot.samples.nfcreading

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.innovatrics.dot.samples.R
import com.innovatrics.dot.samples.ui.gson

class NfcReadingResultFragment : Fragment(R.layout.fragment_nfc_reading_result) {

    private val nfcReadingViewModel: NfcReadingViewModel by activityViewModels()

    private lateinit var imageView: ImageView
    private lateinit var textView: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViews(view)
        setupNfcReadingViewModel()
    }

    private fun setViews(view: View) {
        imageView = view.findViewById(R.id.image)
        textView = view.findViewById(R.id.text)
    }

    private fun setupNfcReadingViewModel() {
        nfcReadingViewModel.state.observe(viewLifecycleOwner) { showResult(it.result!!) }
    }

    private fun showResult(result: NfcReadingResult) {
        imageView.setImageBitmap(result.faceBitmap)
        textView.text = gson.toJson(result)
    }
}
