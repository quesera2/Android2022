plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "que.sera.sera.android2022.data"
    compileSdk = 32

    defaultConfig {
        minSdk = 26
        targetSdk = 32

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
}

dependencies {

    implementation(libs.androidx.core.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.com.google.android.material)

    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.espresso.espresso.core)

    implementation(libs.com.google.dagger.hilt.android)
    kapt(libs.com.google.dagger.hilt.android.compiler)

    kapt(libs.androidx.hilt.hilt.compiler)

    implementation(libs.androidx.datastore.datastore.preferences)

    implementation(platform(libs.com.google.firebase.firebase.bom))
    implementation(libs.com.google.firebase.firebase.analytics)
    implementation(libs.com.google.firebase.firebase.firestore.ktx)
    implementation(libs.org.jetbrains.kotlinx.kotlinx.coroutines.play.services)

    testImplementation(libs.junit)
    testImplementation(libs.io.mockk)

    implementation(projects.library.entity)
}