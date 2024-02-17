plugins {
    id("convention.android-library")
    id("convention.module-core")
    id("dagger.hilt.android.plugin")
}

dependencies {
    implementation(project(":core-android"))
    implementation(project(":core"))
    implementation(project(":core-design"))

    implementation(libs.image.coil.core)
    implementation(libs.view.viewBindingDelegate)

    implementation(libs.di.inject)
    implementation(libs.hilt.core)
    kapt(libs.hilt.compiler)

    implementation(libs.navigation.jetpack.core)

    testImplementation(libs.test.junit.core)
}