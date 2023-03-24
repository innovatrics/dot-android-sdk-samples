package com.innovatrics.dot.samples

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    companion object {
        const val OVERLAY_ALPHA_NORMAL_SCREEN = 0.0f
        const val OVERLAY_ALPHA_DIM_SCREEN = 0.6f
    }

    private val mainViewModel: MainViewModel by viewModels()

    private lateinit var overlayView: View
    private lateinit var progressIndicator: CircularProgressIndicator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setViews()
        setupMainViewModel()
    }

    private fun setViews() {
        overlayView = findViewById(R.id.overlay)
        progressIndicator = findViewById(R.id.progress_indicator)
    }

    private fun setupMainViewModel() {
        mainViewModel.state.observe(this) { state ->
            when (state.isProcessing) {
                true -> dimScreen()
                false -> restoreScreen()
            }
            state.errorMessage?.let {
                Snackbar.make(findViewById(android.R.id.content), it, Snackbar.LENGTH_SHORT).show()
                mainViewModel.notifyErrorMessageShown()
            }
        }
    }

    private fun dimScreen() {
        overlayView.alpha = OVERLAY_ALPHA_DIM_SCREEN
        progressIndicator.show()
    }

    private fun restoreScreen() {
        overlayView.alpha = OVERLAY_ALPHA_NORMAL_SCREEN
        progressIndicator.hide()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        mainViewModel.setIntent(intent)
    }
}
