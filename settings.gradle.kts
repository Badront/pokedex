@file:Suppress("UnstableApiUsage")

pluginManagement {
    repositories {
        google()
        mavenCentral()
        maven("https://plugins.gradle.org/m2/")
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    versionCatalogs {
        register("libs") {
            from(files("build-logic/dependencies/libs.versions.toml"))
        }
    }
    repositories {
        google()
        mavenCentral()
        maven("https://plugins.gradle.org/m2/")
        maven("https://jitpack.io")
    }
}

rootProject.name = "Pokedex"

enableFeaturePreview("VERSION_CATALOGS")

includeBuild("build-logic")

include(":app")
include(":core")
include(":core-design")
include(":core-android")
include(":core-database")
include(":pokemon:pokemon-api")
include(":pokemon:pokemon-impl")
include(":evolution:evolution-api")
include(":evolution:evolution-impl")
include(":item:item-api")
include(":item:item-impl")