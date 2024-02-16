@file:Suppress("UnstableApiUsage")

pluginManagement {
    repositories {
        google()
        mavenCentral()
        maven("https://plugins.gradle.org/m2/")
    }
}
dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("../build-logic/dependencies/libs.versions.toml"))
        }
    }
    repositories {
        google()
        mavenCentral()
        maven("https://plugins.gradle.org/m2/")
    }
}

rootProject.name = "build-logic"

enableFeaturePreview("VERSION_CATALOGS")

include("module-conventions")