plugins {
    id("convention.android-library")
    id("convention.module-core")
    id("dagger.hilt.android.plugin")
}

dependencies {
    implementation(project(":core"))
    implementation(project(":core-android"))
    implementation(project(":core-design"))
    implementation(project(":item:item-api"))
    implementation(project(":pokemon:pokemon-api"))

    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlin.coroutines.core)
    implementation(libs.kotlin.coroutines.android)

    implementation(libs.logging.timber)

    implementation(libs.view.viewBindingDelegate)
    implementation(libs.navigation.jetpack.core)
    implementation(libs.navigation.jetpack.fragment)

    implementation(libs.di.inject)
    implementation(libs.hilt.core)
    kapt(libs.hilt.compiler)

    implementation(libs.image.coil.core)
}