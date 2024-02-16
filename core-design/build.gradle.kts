plugins {
    id("convention.android-library")
    id("convention.module-core")
}

dependencies {
    implementation(libs.kotlin.stdlib)

    implementation(libs.logging.timber)

    implementation(libs.android.support.compat)
    implementation(libs.android.support.core)

    implementation(libs.view.viewBindingDelegate)
}