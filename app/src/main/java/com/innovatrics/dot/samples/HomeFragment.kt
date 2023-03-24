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
import com.innovatrics.dot.face.liveness.magnifeye.MagnifEyeLivenessConfiguration
import com.innovatrics.dot.face.liveness.magnifeye.MagnifEyeLivenessFragment
import com.innovatrics.dot.face.liveness.smile.SmileLivenessConfiguration
import com.innovatrics.dot.face.liveness.smile.SmileLivenessFragment

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var documentAutoCaptureStartButton: Button
    private lateinit var nfcReadingStartButton: Button
    private lateinit var faceAutoCaptureStartButton: Button
    private lateinit var smileLivenessStartButton: Button
    private lateinit var magnifEyeLivenessStartButton: Button
    private lateinit var faceMatcherStartButton: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViews(view)
        setupDocumentAutoCaptureStartButton()
        setupNfcReadingStartButton()
        setupFaceAutoCaptureStartButton()
        setupSmileLivenessStartButton()
        setupMagnifEyeLivenessStartButton()
        setupFaceMatcherStartButton()
    }

    private fun setViews(view: View) {
        documentAutoCaptureStartButton = view.findViewById(R.id.document_auto_capture_start)
        nfcReadingStartButton = view.findViewById(R.id.nfc_reading_start)
        faceAutoCaptureStartButton = view.findViewById(R.id.face_auto_capture_start)
        smileLivenessStartButton = view.findViewById(R.id.smile_liveness_start)
        magnifEyeLivenessStartButton = view.findViewById(R.id.magnifeye_liveness_start)
        faceMatcherStartButton = view.findViewById(R.id.face_matcher_start)
    }

    private fun setupDocumentAutoCaptureStartButton() {
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

    private fun setupFaceAutoCaptureStartButton() {
        faceAutoCaptureStartButton.setOnClickListener {
            val bundle = bundleOf(
                FaceAutoCaptureFragment.CONFIGURATION to FaceAutoCaptureConfiguration.Builder()
                    .qualityAttributes(PassiveLivenessQualityProvider().qualityAttributes)
                    .build()
            )
            findNavController().navigate(R.id.action_HomeFragment_to_BasicFaceAutoCaptureFragment, bundle)
        }
    }

    private fun setupSmileLivenessStartButton() {
        smileLivenessStartButton.setOnClickListener {
            val bundle = bundleOf(SmileLivenessFragment.CONFIGURATION to SmileLivenessConfiguration.Builder().build())
            findNavController().navigate(R.id.action_HomeFragment_to_BasicSmileLivenessFragment, bundle)
        }
    }

    private fun setupMagnifEyeLivenessStartButton() {
        magnifEyeLivenessStartButton.setOnClickListener {
            val bundle = bundleOf(MagnifEyeLivenessFragment.CONFIGURATION to MagnifEyeLivenessConfiguration.Builder().build())
            findNavController().navigate(R.id.action_HomeFragment_to_BasicMagnifEyeLivenessFragment, bundle)
        }
    }

    private fun setupFaceMatcherStartButton() {
        faceMatcherStartButton.setOnClickListener {
            findNavController().navigate(R.id.action_HomeFragment_to_FaceMatcherFragment)
        }
    }
}
