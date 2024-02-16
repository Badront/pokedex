plugins {
    id("convention.android-library")
    id("convention.module-core")
    id("dagger.hilt.android.plugin")
    id("kotlin-parcelize")
}
dependencies {
    implementation(project(":core"))
    implementation(project(":core-design"))
    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlin.coroutines.core)
    implementation(libs.android.viewmodel.core)
    implementation(libs.android.viewmodel.lifecycle)
    implementation(libs.android.viewmodel.common)

    implementation(libs.logging.timber)

    implementation(libs.android.view.recycler)
    implementation(libs.android.view.constraint)

    implementation(libs.navigation.jetpack.core)

    implementation(libs.image.coil.core)
    implementation(libs.palette)

    implementation(libs.room.runtime)
    implementation(libs.room.ktx)

    implementation(libs.hilt.core)
    kapt(libs.hilt.compiler)

    implementation(libs.retrofit.core)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.converter.gson)
    implementation(libs.okhttp.core)
    implementation(libs.okhttp.logging)
}