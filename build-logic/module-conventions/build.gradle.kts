plugins {
    `kotlin-dsl`
}
dependencies {
    implementation(libs.plugin.android.gradle)
    implementation(libs.plugin.kotlin.gradle)
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}