package com.innovatrics.dot.samples.nfcreading

import android.app.Activity
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NfcAdapter
import android.nfc.tech.IsoDep
import android.os.Build

class NfcDispatchController {

    private lateinit var pendingIntent: PendingIntent
    private lateinit var intentFilters: Array<IntentFilter>
    private lateinit var techLists: Array<Array<String>>

    fun setup(context: Context) {
        pendingIntent = createPendingIntent(context)
        intentFilters = arrayOf(IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED))
        techLists = arrayOf(arrayOf(IsoDep::class.java.name))
    }

    private fun createPendingIntent(context: Context): PendingIntent {
        val intent = Intent(context, context.javaClass).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        }
        val flags = resolvePendingIntentFlags()
        return PendingIntent.getActivity(context, 0, intent, flags)
    }

    private fun resolvePendingIntentFlags(): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.FLAG_MUTABLE
        } else {
            PendingIntent.FLAG_UPDATE_CURRENT
        }
    }

    fun enableForegroundDispatch(activity: Activity) {
        NfcAdapter
            .getDefaultAdapter(activity)
            .enableForegroundDispatch(activity, pendingIntent, intentFilters, techLists)
    }

    fun disableForegroundDispatch(activity: Activity) {
        NfcAdapter
            .getDefaultAdapter(activity)
            .disableForegroundDispatch(activity)
    }
}
