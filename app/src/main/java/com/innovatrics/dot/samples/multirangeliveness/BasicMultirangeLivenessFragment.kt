package com.innovatrics.dot.samples.multirangeliveness

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.innovatrics.dot.face.commons.liveness.multirange.MultiRangeLivenessChallengeItem
import com.innovatrics.dot.face.liveness.multirange.MultiRangeLivenessResult
import com.innovatrics.dot.face.liveness.multirange.ui.MultiRangeLivenessFragment
import com.innovatrics.dot.samples.DotSdkViewModel
import com.innovatrics.dot.samples.DotSdkViewModelFactory
import com.innovatrics.dot.samples.R
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class BasicMultirangeLivenessFragment : MultiRangeLivenessFragment() {

    private val dotSdkViewModel: DotSdkViewModel by activityViewModels { DotSdkViewModelFactory(requireActivity().application) }
    private val multiRangeLivenessViewModel: MultiRangeLivenessViewModel by activityViewModels { MultiRangeLivenessViewModelFactory() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupDotSdkViewModel()
        setupMultiRangeLivenessViewModel()
    }

    private fun setupDotSdkViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                dotSdkViewModel.state.collect { state ->
                    if (state.isInitialized) {
                        start()
                    }
                }
            }
        }
        dotSdkViewModel.initializeDotSdkIfNeeded()
    }

    private fun setupMultiRangeLivenessViewModel() {
        multiRangeLivenessViewModel.initializeState()
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                multiRangeLivenessViewModel.state.collectLatest { state ->
                    state.result?.let {
                        findNavController().navigate(R.id.action_BasicMultiRangeLivenessFragment_to_MultiRangeLivenessResultFragment)
                    }
                }
            }
        }
    }

    override fun onFinished(result: MultiRangeLivenessResult) {
        multiRangeLivenessViewModel.process(result)
    }

    override fun provideConfiguration(): Configuration {
        return Configuration(
            challengeSequence = mockChallengeSequenceFromDisService(),
        )
    }

    private fun mockChallengeSequenceFromDisService(): List<MultiRangeLivenessChallengeItem> {
        return listOf(
            MultiRangeLivenessChallengeItem.ONE,
            MultiRangeLivenessChallengeItem.FIVE,
            MultiRangeLivenessChallengeItem.ZERO,
            MultiRangeLivenessChallengeItem.FOUR,
        )
    }

    override fun onNoCameraPermission() {
        throw IllegalStateException("No camera permission.")
    }
}
