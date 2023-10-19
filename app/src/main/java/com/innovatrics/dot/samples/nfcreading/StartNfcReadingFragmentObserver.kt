package com.innovatrics.dot.samples.nfcreading

import android.content.Intent
import androidx.core.util.Consumer
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

class StartNfcReadingFragmentObserver(
    private val activity: FragmentActivity,
    private val onNewIntentListener: Consumer<Intent>,
    private val nfcDispatchController: NfcDispatchController = NfcDispatchController(),
) : DefaultLifecycleObserver {

    init {
        nfcDispatchController.setup(activity)
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        activity.addOnNewIntentListener(onNewIntentListener)
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        nfcDispatchController.enableForegroundDispatch(activity)
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        nfcDispatchController.disableForegroundDispatch(activity)
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        activity.removeOnNewIntentListener(onNewIntentListener)
    }
}
