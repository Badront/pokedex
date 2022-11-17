rootProject.name = "Pokedex"
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

dependencyResolutionManagement {
    versionCatalogs {
        register("libs") {
            from(files("config/libs.versions.toml"))
        }
    }
}