package com.innovatrics.dot.samples.nfcreading

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.innovatrics.dot.samples.R
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class NfcReadingFragment : Fragment(R.layout.fragment_nfc_reading) {

    private val nfcReadingViewModel: NfcReadingViewModel by activityViewModels()

    private lateinit var cancelButton: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViews(view)
        setupCancelButton()
        initNfcTravelDocumentReaderFragment(savedInstanceState)
        setupNfcReadingViewModel()
    }

    private fun setViews(view: View) {
        cancelButton = view.findViewById(R.id.cancel)
    }

    private fun setupCancelButton() {
        cancelButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun initNfcTravelDocumentReaderFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            childFragmentManager.commit {
                replace(R.id.container, DefaultNfcTravelDocumentReaderFragment::class.java, null)
            }
        }
    }

    private fun setupNfcReadingViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                nfcReadingViewModel.state.collectLatest { state ->
                    state.result?.let {
                        findNavController().navigate(R.id.action_NfcReadingFragment_to_NfcReadingResultFragment)
                    }
                }
            }
        }
    }
}
