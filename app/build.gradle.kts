plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("com.google.gms.google-services")
}

android {
    compileSdk = 33
    namespace = "que.sera.sera.android2022"

    defaultConfig {
        applicationId = "que.sera.sera.android2022"
        minSdk = 26
        targetSdk = 33
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
        kotlinCompilerExtensionVersion = libs.versions.androidx.compose.compiler.get()
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(projects.core.coreUi)
    implementation(projects.feature.todoList)
    implementation(projects.feature.todoDetail)

    implementation(libs.androidx.core.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.com.google.android.material)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.activity.compose)
    implementation(libs.androidx.compose.material3)

    implementation(libs.com.google.dagger.hilt.android)
    kapt(libs.com.google.dagger.hilt.android.compiler)
    implementation(libs.androidx.lifecycle.lifecycle.viewmodel.compose)
    kapt(libs.androidx.hilt.hilt.compiler)

    implementation(libs.androidx.hilt.hilt.navigation.compose)
    implementation(libs.com.google.accompanist.accompanist.navigation.animation)
    implementation(libs.com.google.accompanist.accompanist.navigation.material)
    implementation(libs.com.google.accompanist.accompanist.systemuicontroller)

    testImplementation(libs.junit)
    testImplementation(libs.io.mockk)
}
