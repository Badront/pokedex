@file:Suppress("UnstableApiUsage")

import com.badront.ext.withVersionCatalog

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-parcelize")
    id("kotlin-kapt")
    id("convention.module-core")
}
android {
    buildFeatures {
        viewBinding = true
        // compose = true
    }

    project.withVersionCatalog { libs ->
        composeOptions {
            // kotlinCompilerExtensionVersion = libs.versions.kotlin.compiler.extension.version.get()
        }
    }
    kotlinOptions {
        freeCompilerArgs =
            freeCompilerArgs + "-Xopt-in=androidx.compose.material3.ExperimentalMaterial3Api"
        freeCompilerArgs =
            freeCompilerArgs + "-Xopt-in=androidx.compose.foundation.layout.ExperimentalLayoutApi"
    }
}
