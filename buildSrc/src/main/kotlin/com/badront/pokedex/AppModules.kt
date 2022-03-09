package com.badront.pokedex

object AppModules {
    const val app = ":app"
    const val core = ":core"

    object Pokemon {
        private const val DIR = ":pokemon"
        const val api = "$DIR:pokemon-api"
        const val impl = "$DIR:pokemon-impl"
    }
}