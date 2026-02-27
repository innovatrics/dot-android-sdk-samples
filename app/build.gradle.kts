plugins {
    alias(libs.plugins.com.android.application)
    alias(libs.plugins.org.jlleitschuh.gradle.ktlint)
}

kotlin {
    jvmToolchain(jdkVersion = 21)
}

android {
    namespace = "com.innovatrics.dot.samples"
    compileSdk {
        version = release(version = 36)
    }

    defaultConfig {
        applicationId = "com.innovatrics.dot.samples"
        minSdk {
            version = release(version = 24)
        }
        targetSdk {
            version = release(version = 36)
        }
        versionCode = 1
        versionName = "4.69.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    lint {
        disable += "MonochromeLauncherIcon"
    }

    packaging {
        resources {
            excludes += listOf(
                "**/jnidispatch.dll",
                "**/libjnidispatch.a",
                "**/libjnidispatch.jnilib",
                "**/*.proto",
            )
        }
    }
}

dependencies {
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.com.google.android.material)
    implementation(libs.com.google.code.gson)
    implementation(libs.com.innovatrics.dot.document)
    implementation(libs.com.innovatrics.dot.face.detection.fast)
    implementation(libs.com.innovatrics.dot.face.expression.neutral)
    implementation(libs.com.innovatrics.dot.nfc)
    implementation(libs.com.innovatrics.dot.palm.detection)
    implementation(libs.dev.keiji.jp2.android)
}

ktlint {
    android = true
}
