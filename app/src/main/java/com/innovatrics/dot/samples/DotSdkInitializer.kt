package com.innovatrics.dot.samples

import android.content.Context
import com.innovatrics.dot.core.DotSdk
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

object DotSdkInitializer {

    private val mutex = Mutex()
    private val initializeDotSdk = InitializeDotSdk()

    suspend fun initializeIfNeeded(context: Context) {
        mutex.withLock {
            if (!DotSdk.isInitialized()) {
                initializeDotSdk(context.applicationContext)
            }
        }
    }
}
