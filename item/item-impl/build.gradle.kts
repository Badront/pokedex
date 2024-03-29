plugins {
    id("convention.android-library")
    id("convention.module-core")
    id("androidx.navigation.safeargs.kotlin")
    id("dagger.hilt.android.plugin")
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:design"))
    implementation(project(":core:android"))
    implementation(project(":item:item-api"))

    implementation(libs.logging.timber)

    implementation(libs.di.inject)
    implementation(libs.hilt.core)
    kapt(libs.hilt.compiler)

    implementation(libs.retrofit.core)
    implementation(libs.converter.gson)
}