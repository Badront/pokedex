package com.badront.pokedex

object AppModules {
    const val app = ":app"
    const val core = ":core"
    const val coreDesign = ":core-design"
    const val coreAndroid = ":core-android"

    object Pokemon {
        private const val DIR = ":pokemon"
        const val api = "$DIR:pokemon-api"
        const val impl = "$DIR:pokemon-impl"
    }

    object Evolution {
        private const val DIR = ":evolution"
        const val api = "$DIR:evolution-api"
        const val impl = "$DIR:evolution-impl"
    }
}