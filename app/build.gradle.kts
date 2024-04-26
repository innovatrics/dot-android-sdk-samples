plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jlleitschuh.gradle.ktlint")
}

android {
    namespace = "com.innovatrics.dot.samples"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.innovatrics.dot.samples"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "4.25.0"
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
        disable += "conLocation"
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
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-fragment-ktx:${rootProject.extra["navigationVersion"]}")
    implementation("androidx.navigation:navigation-ui-ktx:${rootProject.extra["navigationVersion"]}")
    implementation("com.github.Tgo1014:JP2ForAndroid:1.0.4")
    implementation("com.google.android.material:material:1.11.0")
    implementation("com.innovatrics.dot:dot-document:${rootProject.extra["dotVersion"]}")
    implementation("com.innovatrics.dot:dot-face-detection-fast:${rootProject.extra["dotVersion"]}")
    implementation("com.innovatrics.dot:dot-face-expression-neutral:${rootProject.extra["dotVersion"]}")
    implementation("com.innovatrics.dot:dot-nfc:${rootProject.extra["dotVersion"]}")
}

ktlint {
    android = true
}
