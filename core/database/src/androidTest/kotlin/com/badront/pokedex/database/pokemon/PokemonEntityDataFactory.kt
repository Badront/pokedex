package com.badront.pokedex.database.pokemon

import com.badront.pokedex.database.DataFactory
import com.badront.pokedex.database.pokemon.model.PokemonEntity

object PokemonEntityDataFactory {
    fun create(): PokemonEntity {
        return PokemonEntity(
            id = DataFactory.randomInt(),
            name = DataFactory.randomString(),
            image = DataFactory.randomString(),
            number = DataFactory.randomInt()
        )
    }
}