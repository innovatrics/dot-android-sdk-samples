package com.innovatrics.dot.samples.image

import android.graphics.Bitmap
import com.gemalto.jp2.JP2Decoder
import com.innovatrics.dot.image.BitmapFactory
import com.innovatrics.dot.image.Image
import com.innovatrics.dot.image.ImageFormat

fun Image.toBitmap(): Bitmap {
    return when (format) {
        ImageFormat.JPEG_2000 -> createBitmapFromJpeg2000(bytes)
        ImageFormat.JPEG -> createBitmapFromJpeg(bytes)
        else -> BitmapFactory.create(image = this)
    }
}

private fun createBitmapFromJpeg2000(bytes: ByteArray): Bitmap {
    val jp2Decoder = JP2Decoder(bytes)
    return jp2Decoder.decode()
}

private fun createBitmapFromJpeg(bytes: ByteArray): Bitmap {
    return android.graphics.BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
}
