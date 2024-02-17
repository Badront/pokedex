plugins {
    id("convention.android-library")
    id("convention.module-core")
    id("dagger.hilt.android.plugin")
}

dependencies {
    implementation(project(":core"))
    implementation(project(":core-design"))
    implementation(project(":core-android"))
    implementation(project(":evolution:evolution-api"))
    implementation(project(":pokemon:pokemon-api"))
    implementation(project(":item:item-api"))

    implementation(libs.kotlin.coroutines.android)

    implementation(libs.logging.timber)

    implementation(libs.hilt.core)
    kapt(libs.hilt.compiler)

    implementation(libs.retrofit.core)
    implementation(libs.converter.gson)

    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
}