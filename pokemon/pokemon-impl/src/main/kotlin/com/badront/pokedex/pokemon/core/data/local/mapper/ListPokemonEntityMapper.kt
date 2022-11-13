package com.badront.pokedex.pokemon.core.data.local.mapper

import com.badront.pokedex.database.pokemon.model.PokemonEntity
import com.badront.pokedex.pokemon.core.domain.model.Pokemon
import javax.inject.Inject

class ListPokemonEntityMapper @Inject constructor() {
    fun map(entity: PokemonEntity): Pokemon {
        return Pokemon(
            id = entity.id,
            name = entity.name,
            number = entity.number,
            image = entity.image
        )
    }

    fun map(model: Pokemon): PokemonEntity {
        return PokemonEntity(
            id = model.id,
            name = model.name,
            image = model.image,
            number = model.number
        )
    }
}