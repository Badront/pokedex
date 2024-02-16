plugins {
    id("convention.android-library")
    id("convention.module-core")
    id("androidx.navigation.safeargs.kotlin")
    id("dagger.hilt.android.plugin")
}
dependencies {
    implementation(project(":core"))
    implementation(project(":core-design"))
    implementation(project(":core-android"))
    implementation(project(":core-database"))
    implementation(project(":pokemon:pokemon-api"))
    implementation(project(":evolution:evolution-api"))

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