package com.innovatrics.dot.samples.nfcreading

import com.innovatrics.dot.mrz.MachineReadableZone
import com.innovatrics.dot.nfc.access.Password

fun createPassword(machineReadableZone: MachineReadableZone) = when {
    machineReadableZone.td1 != null -> Password.MachineReadableZone(
        machineReadableZone.td1!!.documentNumber.value,
        machineReadableZone.td1!!.dateOfExpiry.value,
        machineReadableZone.td1!!.dateOfBirth.value,
    )

    machineReadableZone.td2 != null -> Password.MachineReadableZone(
        machineReadableZone.td2!!.documentNumber.value,
        machineReadableZone.td2!!.dateOfExpiry.value,
        machineReadableZone.td2!!.dateOfBirth.value,
    )

    machineReadableZone.td3 != null -> Password.MachineReadableZone(
        machineReadableZone.td3!!.passportNumber.value,
        machineReadableZone.td3!!.dateOfExpiry.value,
        machineReadableZone.td3!!.dateOfBirth.value,
    )

    else -> throw IllegalStateException("Machine Readable Zone must be present.")
}
