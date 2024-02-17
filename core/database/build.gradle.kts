plugins {
    id("convention.android-library")
    id("convention.module-core")
    id("dagger.hilt.android.plugin")
}
android {
    defaultConfig {
        javaCompileOptions {
            annotationProcessorOptions {
                arguments["room.schemaLocation"] = "$projectDir/schemas"
                arguments["room.incremental"] = "true"
                arguments["room.expandProjection"] = "true"
            }
        }
    }
}
dependencies {
    implementation(project(":core:common"))

    implementation(libs.logging.timber)

    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    kapt(libs.room.compiler)

    implementation(libs.hilt.core)
    implementation(libs.di.inject)
    kapt(libs.hilt.compiler)

    testImplementation(libs.test.junit.core)
    androidTestImplementation(libs.test.android.runner)
    androidTestImplementation(libs.test.junit.android)
    androidTestImplementation(libs.test.android.core)
}