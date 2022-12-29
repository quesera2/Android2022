plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "que.sera.sera.todo_detail"
    compileSdk = 33

    defaultConfig {
        minSdk = 26
        targetSdk = 33

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.androidx.compose.compiler.get()
    }
}

dependencies {

    implementation(projects.core.coreUi)

    implementation(libs.androidx.core.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.com.google.android.material)
    implementation(platform(libs.androidx.compose.bom))
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

    implementation(libs.com.google.accompanist.accompanist.themeadapter.material3)

    testImplementation(libs.io.mockk)

    implementation(projects.library.data)
    implementation(projects.library.entity)
}