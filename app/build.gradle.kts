import com.badront.pokedex.AppModules

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        applicationId = "com.badront.pokedex"
        versionCode = 1
        versionName = "1.0"

        multiDexEnabled = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }

    buildFeatures {
        viewBinding = true
    }

    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
            versionNameSuffix = "-debug"
            applicationIdSuffix = ".debug"
        }
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "../config/proguard-rules.pro")
        }
    }
}

dependencies {
    implementation(project(AppModules.core))
    implementation(project(AppModules.coreDesign))
    implementation(project(AppModules.coreAndroid))
    implementation(project(AppModules.coreDB))
    implementation(project(AppModules.Pokemon.api))
    implementation(project(AppModules.Pokemon.impl))
    implementation(project(AppModules.Item.api))
    implementation(project(AppModules.Item.impl))
    implementation(project(AppModules.Evolution.api))
    implementation(project(AppModules.Evolution.impl))

    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlin.coroutines.core)

    implementation(libs.di.inject)
    implementation(libs.hilt.core)
    kapt(libs.hilt.compiler)

    implementation(libs.retrofit.core)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.okhttp.logging)
    implementation(libs.converter.gson)

    implementation(libs.room.runtime)

    implementation(libs.logging.timber)
    implementation(libs.leakcanary)

    implementation(libs.image.coil.core)

    implementation(libs.android.support.compat)
}