package com.innovatrics.dot.samples.ui

import com.google.gson.Gson
import com.google.gson.GsonBuilder

val gson: Gson by lazy {
    GsonBuilder()
        .setExclusionStrategies(ResultExclusionStrategy())
        .registerTypeAdapter(ByteArray::class.java, ByteArraySerializer())
        .setPrettyPrinting()
        .disableHtmlEscaping()
        .create()
}
