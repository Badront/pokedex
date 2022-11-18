import com.badront.pokedex.AppModules

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id("kotlin-parcelize")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()

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
}

dependencies {
    implementation(project(AppModules.coreAndroid))
    implementation(project(AppModules.core))
    implementation(project(AppModules.coreDesign))

    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlin.coroutines.core)

    implementation(libs.image.coil.core)
    implementation(libs.view.viewBindingDelegate)

    implementation(libs.di.inject)
    implementation(libs.hilt.core)
    kapt(libs.hilt.compiler)

    implementation(libs.navigation.jetpack.core)

    testImplementation(libs.test.junit.core)
}