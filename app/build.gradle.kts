plugins {
    id("convention.android-application")
    id("convention.module-core")
    id("dagger.hilt.android.plugin")
}

android {
    defaultConfig {
        applicationId = "com.badront.pokedex"
        versionCode = 1
        versionName = "1.0"
    }
}

dependencies {
    implementation(project(":core"))
    implementation(project(":core-design"))
    implementation(project(":core-android"))
    implementation(project(":core-database"))
    implementation(project(":pokemon:pokemon-api"))
    implementation(project(":pokemon:pokemon-impl"))
    implementation(project(":item:item-api"))
    implementation(project(":item:item-impl"))
    implementation(project(":evolution:evolution-api"))
    implementation(project(":evolution:evolution-impl"))

    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlin.coroutines.core)

    implementation(libs.di.inject)
    implementation(libs.hilt.core)
    kapt(libs.hilt.compiler)

    implementation(libs.retrofit.core)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.okhttp.logging)
    implementation(libs.converter.gson)

    implementation(libs.room.runtime)

    implementation(libs.logging.timber)
    implementation(libs.leakcanary)

    implementation(libs.image.coil.core)

    implementation(libs.android.support.compat)
}