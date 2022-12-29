// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(libs.com.android.tools.build.gradle)
        classpath(libs.org.jetbrains.kotlin.kotlin.gradle.plugin)
        classpath(libs.com.google.gms.google.services)
        classpath(libs.com.google.dagger.hilt.android.gradle.plugin)
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

plugins {
    alias(libs.plugins.com.autonomousapps.dependency.analysis)
}

task<Delete>("clean") {
    delete = setOf(rootProject.buildDir)
}
