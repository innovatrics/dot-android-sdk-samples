package com.innovatrics.dot.samples.nfcreading

import com.innovatrics.dot.document.mrz.MachineReadableZone
import com.innovatrics.dot.nfc.NfcKey

fun createNfcKey(machineReadableZone: MachineReadableZone): NfcKey {
    return when {
        machineReadableZone.td1 != null -> NfcKey(
            machineReadableZone.td1!!.documentNumber.value,
            machineReadableZone.td1!!.dateOfExpiry.value,
            machineReadableZone.td1!!.dateOfBirth.value,
        )
        machineReadableZone.td2 != null -> NfcKey(
            machineReadableZone.td2!!.documentNumber.value,
            machineReadableZone.td2!!.dateOfExpiry.value,
            machineReadableZone.td2!!.dateOfBirth.value,
        )
        machineReadableZone.td3 != null -> NfcKey(
            machineReadableZone.td3!!.passportNumber.value,
            machineReadableZone.td3!!.dateOfExpiry.value,
            machineReadableZone.td3!!.dateOfBirth.value,
        )
        else -> throw IllegalStateException("Machine Readable Zone must be present.")
    }
}
