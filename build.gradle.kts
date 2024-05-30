plugins {
    id("com.android.application") version "8.3.2" apply false
    id("com.android.library") version "8.3.2" apply false
    id("org.jetbrains.kotlin.android") version "1.8.22" apply false
    id("org.jlleitschuh.gradle.ktlint") version "12.1.0" apply false
}

project.extra["dotVersion"] = "7.5.5"
project.extra["navigationVersion"] = "2.7.7"
