import com.badront.pokedex.AppModules

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    id("kotlin-parcelize")
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
    implementation(project(AppModules.core))
    implementation(project(AppModules.coreDesign))
    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlin.coroutines.core)
    implementation(libs.android.viewmodel.core)
    implementation(libs.android.viewmodel.lifecycle)
    implementation(libs.android.viewmodel.common)

    implementation(libs.logging.timber)

    implementation(libs.android.view.recycler)
    implementation(libs.android.view.constraint)

    implementation(libs.navigation.jetpack.core)

    implementation(libs.image.coil.core)
    implementation(libs.palette)

    implementation(libs.room.runtime)
    implementation(libs.room.ktx)

    implementation(libs.hilt.core)
    kapt(libs.hilt.compiler)

    implementation(libs.retrofit.core)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.converter.gson)
    implementation(libs.okhttp.core)
    implementation(libs.okhttp.logging)
}