package com.innovatrics.dot.samples.faceautocapture.customui

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.innovatrics.dot.face.autocapture.ui.UiState
import com.innovatrics.dot.face.commons.autocapture.Phase
import com.innovatrics.dot.samples.R

internal class FaceAutoCapturePhase(
    private val context: Context,
    private val textView: TextView,
) {

    fun update(uiState: UiState) {
        when (uiState) {
            is UiState.Initializing -> disableInstruction()
            is UiState.Idle -> disableInstruction()
            is UiState.Running -> updateTextView(uiState)
        }
    }

    private fun disableInstruction() {
        textView.visibility = View.GONE
    }

    private fun updateTextView(uiState: UiState.Running) {
        updateText(uiState)
        updateColor(uiState)
    }

    private fun updateText(uiState: UiState.Running) {
        uiState.phase?.let { refreshInstructionTextView(resourceId = it.toResourceId()) } ?: disableInstruction()
    }

    private fun Phase.toResourceId(): Int {
        return when (this) {
            Phase.PRE_CANDIDATE_SELECTION -> R.string.sample_custom_ui_face_auto_capture_phase_pre_candidate_selection
            Phase.CANDIDATE_SELECTION -> R.string.sample_custom_ui_face_auto_capture_phase_candidate_selection
        }
    }

    private fun refreshInstructionTextView(resourceId: Int) {
        textView.setText(resourceId)
        textView.visibility = View.VISIBLE
    }

    private fun updateColor(uiState: UiState.Running) {
        if (uiState.phase == Phase.CANDIDATE_SELECTION) {
            setInstructionColors(
                textColorResourceId = com.innovatrics.dot.ui.R.color.dot_instruction_candidate_selection_text,
                backgroundColorResourceId = com.innovatrics.dot.ui.R.color.dot_instruction_candidate_selection_background,
            )
        } else {
            setInstructionColors(
                textColorResourceId = com.innovatrics.dot.ui.R.color.dot_instruction_text,
                backgroundColorResourceId = com.innovatrics.dot.ui.R.color.dot_instruction_background,
            )
        }
    }

    private fun setInstructionColors(textColorResourceId: Int, backgroundColorResourceId: Int) {
        setInstructionTextColor(textColorResourceId)
        setInstructionBackgroundColor(backgroundColorResourceId)
    }

    private fun setInstructionTextColor(resourceId: Int) {
        val color = ContextCompat.getColor(context, resourceId)
        textView.setTextColor(color)
    }

    private fun setInstructionBackgroundColor(resourceId: Int) {
        val color = ContextCompat.getColor(context, resourceId)
        textView.background?.apply {
            mutate()
            setTint(color)
        }
    }
}
