plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("com.google.gms.google-services")
}

android {
    compileSdk = 32

    defaultConfig {
        applicationId = "que.sera.sera.android2022"
        minSdk = 26
        targetSdk = 32
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs += "-opt-in=kotlin.RequiresOptIn"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.2.0-beta03"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.com.google.android.material)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.ui.tooling.preview)
    implementation(libs.androidx.lifecycle.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.activity.compose)
    implementation(libs.androidx.compose.material3)
    testImplementation(libs.junit)

    debugImplementation(libs.androidx.compose.ui.ui.tooling)
    implementation(libs.androidx.window)

    implementation(libs.com.google.dagger.hilt.android)
    kapt(libs.com.google.dagger.hilt.android.compiler)
    implementation(libs.androidx.lifecycle.lifecycle.viewmodel.compose)

    implementation(libs.androidx.navigation.navigation.compose)

    implementation(libs.com.google.accompanist.accompanist.systemuicontroller)

    kapt(libs.androidx.hilt.hilt.compiler)

    implementation(libs.androidx.hilt.hilt.navigation.compose)
    implementation(libs.com.google.accompanist.accompanist.navigation.animation)
    implementation(libs.com.google.accompanist.accompanist.navigation.material)

    implementation(libs.com.google.android.material.compose.theme.adapter.get3())

    testImplementation(libs.io.mockk)

    implementation(projects.library.data)
    implementation(projects.library.entity)
}
