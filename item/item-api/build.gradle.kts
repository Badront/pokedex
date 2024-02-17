plugins {
    id("convention.android-library")
    id("convention.module-core")
    id("dagger.hilt.android.plugin")
}

dependencies {
    implementation(project(":core"))
    implementation(project(":core-android"))
    implementation(project(":core-design"))

    implementation(libs.di.inject)
    implementation(libs.hilt.core)
    kapt(libs.hilt.compiler)

    implementation(libs.image.coil.core)
    implementation(libs.view.viewBindingDelegate)
}