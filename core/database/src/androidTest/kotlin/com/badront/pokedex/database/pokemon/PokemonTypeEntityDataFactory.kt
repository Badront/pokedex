package com.badront.pokedex.database.pokemon

import com.badront.pokedex.database.DataFactory
import com.badront.pokedex.database.pokemon.model.PokemonTypeEntity

object PokemonTypeEntityDataFactory {
    fun create(pokemonId: Int = DataFactory.randomInt()): PokemonTypeEntity {
        return PokemonTypeEntity(
            pokemonId = pokemonId,
            type = DataFactory.randomString(),
            order = DataFactory.randomInt()
        )
    }
}