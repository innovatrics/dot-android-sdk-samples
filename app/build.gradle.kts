plugins {
    alias(libs.plugins.com.android.application)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.org.jlleitschuh.gradle.ktlint)
}

android {
    namespace = "com.innovatrics.dot.samples"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.innovatrics.dot.samples"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "4.39.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    lint {
        disable += "MonochromeLauncherIcon"
    }

    packaging {
        resources {
            excludes.add("**/libjnidispatch.a")
            excludes.add("**/jnidispatch.dll")
            excludes.add("**/libjnidispatch.jnilib")
            excludes.add("**/*.proto")
        }
    }
}

dependencies {
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.com.github.tgo1014.jp2ForAndroid)
    implementation(libs.com.google.android.material)
    implementation(libs.com.innovatrics.dot.document)
    implementation(libs.com.innovatrics.dot.face.detection.fast)
    implementation(libs.com.innovatrics.dot.face.expression.neutral)
    implementation(libs.com.innovatrics.dot.nfc)
    implementation(libs.com.innovatrics.dot.palm.detection)
}

ktlint {
    android = true
}
