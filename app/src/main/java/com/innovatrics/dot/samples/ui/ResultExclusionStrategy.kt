package com.innovatrics.dot.samples.ui

import android.graphics.Bitmap
import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes

class ResultExclusionStrategy : ExclusionStrategy {

    private val excludeFieldNames = listOf(
        "a",
        "debugInfo",
    )
    private val excludeClasses = listOf(
        Bitmap::class.java,
    )

    override fun shouldSkipField(fieldAttributes: FieldAttributes): Boolean {
        return excludeFieldNames.contains(fieldAttributes.name)
    }

    override fun shouldSkipClass(clazz: Class<*>): Boolean {
        return excludeClasses.contains(clazz)
    }
}
