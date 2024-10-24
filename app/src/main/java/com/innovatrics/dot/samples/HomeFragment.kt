package com.innovatrics.dot.samples

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var documentAutoCaptureStartButton: Button
    private lateinit var nfcReadingStartButton: Button
    private lateinit var faceAutoCaptureStartButton: Button
    private lateinit var smileLivenessStartButton: Button
    private lateinit var magnifEyeLivenessStartButton: Button
    private lateinit var palmAutoCaptureStartButton: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViews(view)
        setupDocumentAutoCaptureStartButton()
        setupNfcReadingStartButton()
        setupFaceAutoCaptureStartButton()
        setupSmileLivenessStartButton()
        setupMagnifEyeLivenessStartButton()
        setupPalmAutoCaptureStartButton()
    }

    private fun setViews(view: View) {
        documentAutoCaptureStartButton = view.findViewById(R.id.document_auto_capture_start)
        nfcReadingStartButton = view.findViewById(R.id.nfc_reading_start)
        faceAutoCaptureStartButton = view.findViewById(R.id.face_auto_capture_start)
        smileLivenessStartButton = view.findViewById(R.id.smile_liveness_start)
        magnifEyeLivenessStartButton = view.findViewById(R.id.magnifeye_liveness_start)
        palmAutoCaptureStartButton = view.findViewById(R.id.palm_auto_capture_start)
    }

    private fun setupDocumentAutoCaptureStartButton() {
        documentAutoCaptureStartButton.setOnClickListener {
            findNavController().navigate(R.id.action_HomeFragment_to_BasicDocumentAutoCaptureFragment)
        }
    }

    private fun setupNfcReadingStartButton() {
        nfcReadingStartButton.setOnClickListener {
            findNavController().navigate(R.id.action_HomeFragment_to_PasswordCaptureFragment)
        }
    }

    private fun setupFaceAutoCaptureStartButton() {
        faceAutoCaptureStartButton.setOnClickListener {
            findNavController().navigate(R.id.action_HomeFragment_to_BasicFaceAutoCaptureFragment)
        }
    }

    private fun setupSmileLivenessStartButton() {
        smileLivenessStartButton.setOnClickListener {
            findNavController().navigate(R.id.action_HomeFragment_to_BasicSmileLivenessFragment)
        }
    }

    private fun setupMagnifEyeLivenessStartButton() {
        magnifEyeLivenessStartButton.setOnClickListener {
            findNavController().navigate(R.id.action_HomeFragment_to_BasicMagnifEyeLivenessFragment)
        }
    }

    private fun setupPalmAutoCaptureStartButton() {
        palmAutoCaptureStartButton.setOnClickListener {
            findNavController().navigate(R.id.action_HomeFragment_to_BasicPalmAutoCaptureFragment)
        }
    }
}
