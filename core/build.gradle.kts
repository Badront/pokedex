plugins {
    id("convention.android-library")
    id("convention.module-core")
    id("kotlin-parcelize")
}

dependencies {
    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlin.coroutines.core)
    implementation(libs.kotlin.coroutines.android)

    implementation(libs.android.support.annotation)

    implementation(libs.di.inject)

    testImplementation(libs.test.junit.core)
}