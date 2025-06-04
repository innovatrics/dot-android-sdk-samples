plugins {
    alias(libs.plugins.com.android.application)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.org.jlleitschuh.gradle.ktlint)
}

kotlin {
    jvmToolchain(jdkVersion = 17)
}

android {
    namespace = "com.innovatrics.dot.samples"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.innovatrics.dot.samples"
        minSdk = 21
        targetSdk = 35
        versionCode = 1
        versionName = "4.52.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    implementation(libs.com.github.tgo1014.jp2ForAndroid)
    implementation(libs.com.google.android.material)
    implementation(libs.com.google.code.gson)
    implementation(libs.com.innovatrics.dot.document)
    implementation(libs.com.innovatrics.dot.face.detection.fast)
    implementation(libs.com.innovatrics.dot.face.expression.neutral)
    implementation(libs.com.innovatrics.dot.nfc)
    implementation(libs.com.innovatrics.dot.palm.detection)
}

ktlint {
    android = true
}
