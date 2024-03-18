package com.innovatrics.dot.samples.io

import java.io.File

interface ResourceCopier {

    fun copyToFile(resourceId: Int, destinationFile: File)
}
