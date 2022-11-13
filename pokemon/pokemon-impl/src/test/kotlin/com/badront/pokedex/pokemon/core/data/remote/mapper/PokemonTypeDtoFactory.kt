package com.badront.pokedex.pokemon.core.data.remote.mapper

import com.badront.pokedex.core.data.remote.model.NamedApiResourceDto
import com.badront.pokedex.pokemon.DataFactory
import com.badront.pokedex.pokemon.core.data.remote.model.PokemonTypeDto

object PokemonTypeDtoFactory {
    fun randomValid(name: String = "normal"): PokemonTypeDto {
        return PokemonTypeDto(
            slot = DataFactory.randomInt(),
            type = NamedApiResourceDto(
                name = name,
                url = DataFactory.randomString()
            )
        )
    }

    fun randomInvalid(): PokemonTypeDto {
        return PokemonTypeDto(
            slot = DataFactory.randomInt(),
            type = NamedApiResourceDto(
                name = DataFactory.randomString(),
                url = DataFactory.randomString()
            )
        )
    }
}