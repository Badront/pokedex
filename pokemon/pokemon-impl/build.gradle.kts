import com.badront.pokedex.AppModules

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id("kotlin-parcelize")
    id("androidx.navigation.safeargs.kotlin")
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
    implementation(project(AppModules.core))
    implementation(project(AppModules.coreDesign))
    implementation(project(AppModules.coreAndroid))
    implementation(project(AppModules.coreDB))
    implementation(project(AppModules.Pokemon.api))
    implementation(project(AppModules.Evolution.api))

    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlin.coroutines.core)

    implementation(libs.android.viewmodel.core)

    implementation(libs.logging.timber)

    implementation(libs.di.inject)
    implementation(libs.hilt.core)
    kapt(libs.hilt.compiler)

    implementation(libs.navigation.jetpack.core)
    implementation(libs.navigation.jetpack.fragment)

    implementation(libs.retrofit.core)
    implementation(libs.converter.gson)

    implementation(libs.room.runtime)

    implementation(libs.android.support.core)
    implementation(libs.android.support.fragments)

    implementation(libs.view.viewBindingDelegate)
    implementation(libs.android.view.recycler)
    implementation(libs.android.view.swipeRefresh)
    implementation(libs.view.flexbox)
    implementation(libs.image.coil.core)

    testImplementation(libs.test.junit.core)
    testImplementation(libs.test.mockito.core)
    testImplementation(libs.test.mockito.kotlin)
}