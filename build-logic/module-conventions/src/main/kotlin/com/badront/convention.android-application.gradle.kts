@file:Suppress("UnstableApiUsage")

import com.android.build.gradle.internal.api.BaseVariantOutputImpl
import com.badront.ext.withVersionCatalog

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-parcelize")
    id("kotlin-kapt")
    id("convention.module-core")
}

android {
    defaultConfig {
        multiDexEnabled = true
    }
    buildTypes {
        named("debug").configure {
            versionNameSuffix = "-debug"
            applicationIdSuffix = ".debug"
        }
        named("release") {
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "../build-logic/proguard-rules.pro")
        }
    }
    buildFeatures {
        viewBinding = true
        // compose = true
    }

    applicationVariants.all {
        val variant = this

        variant.outputs
            .map { it as BaseVariantOutputImpl }
            .forEach { output ->
                output.outputFileName = "${variant.versionName}.${variant.versionCode}.apk"
            }
    }
    project.withVersionCatalog { libs ->
        composeOptions {
            // kotlinCompilerExtensionVersion = libs.versions.kotlin.compiler.extension.version.get()
        }
    }
}