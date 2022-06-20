package com.innovatrics.dot.samples

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.innovatrics.dot.document.autocapture.DocumentAutoCaptureConfiguration
import com.innovatrics.dot.document.autocapture.DocumentAutoCaptureFragment
import com.innovatrics.dot.face.autocapture.FaceAutoCaptureConfiguration
import com.innovatrics.dot.face.autocapture.FaceAutoCaptureFragment
import com.innovatrics.dot.face.autocapture.quality.PassiveLivenessQualityProvider
import com.innovatrics.dot.face.liveness.smile.SmileLivenessConfiguration
import com.innovatrics.dot.face.liveness.smile.SmileLivenessFragment

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var documentAutoCaptureStartButton: Button
    private lateinit var nfcReadingStartButton: Button
    private lateinit var faceAutoCaptureStartButton: Button
    private lateinit var smileLivenessStartButton: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViews(view)
        setupDocumentAutoCaptureButton()
        setupNfcReadingStartButton()
        setupFaceAutoCaptureButton()
        setupSmileLivenessButton()
    }

    private fun setViews(view: View) {
        documentAutoCaptureStartButton = view.findViewById(R.id.document_auto_capture_start)
        nfcReadingStartButton = view.findViewById(R.id.nfc_reading_start)
        faceAutoCaptureStartButton = view.findViewById(R.id.face_auto_capture_start)
        smileLivenessStartButton = view.findViewById(R.id.smile_liveness_start)
    }

    private fun setupDocumentAutoCaptureButton() {
        documentAutoCaptureStartButton.setOnClickListener {
            val bundle = bundleOf(DocumentAutoCaptureFragment.CONFIGURATION to DocumentAutoCaptureConfiguration.Builder().build())
            findNavController().navigate(R.id.action_HomeFragment_to_BasicDocumentAutoCaptureFragment, bundle)
        }
    }

    private fun setupNfcReadingStartButton() {
        nfcReadingStartButton.setOnClickListener {
            val bundle = bundleOf(
                DocumentAutoCaptureFragment.CONFIGURATION to DocumentAutoCaptureConfiguration.Builder()
                    .mrzReadingEnabled(true)
                    .build()
            )
            findNavController().navigate(R.id.action_HomeFragment_to_NfcKeyCaptureFragment, bundle)
        }
    }

    private fun setupFaceAutoCaptureButton() {
        faceAutoCaptureStartButton.setOnClickListener {
            val bundle = bundleOf(
                FaceAutoCaptureFragment.CONFIGURATION to FaceAutoCaptureConfiguration.Builder()
                    .qualityAttributes(PassiveLivenessQualityProvider().qualityAttributes)
                    .build()
            )
            findNavController().navigate(R.id.action_HomeFragment_to_BasicFaceAutoCaptureFragment, bundle)
        }
    }

    private fun setupSmileLivenessButton() {
        smileLivenessStartButton.setOnClickListener {
            val bundle = bundleOf(SmileLivenessFragment.CONFIGURATION to SmileLivenessConfiguration.Builder().build())
            findNavController().navigate(R.id.action_HomeFragment_to_BasicSmileLivenessFragment, bundle)
        }
    }
}
