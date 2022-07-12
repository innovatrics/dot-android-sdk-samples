package com.innovatrics.dot.samples.facematcher

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoadBitmapUseCase(
    private val resources: Resources,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) {

    suspend operator fun invoke(resourceId: Int): Bitmap = withContext(ioDispatcher) {
        BitmapFactory.decodeResource(resources, resourceId)
    }
}
