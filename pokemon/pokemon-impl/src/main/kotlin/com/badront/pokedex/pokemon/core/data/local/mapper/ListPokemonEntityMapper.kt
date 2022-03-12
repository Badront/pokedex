package com.badront.pokedex.pokemon.core.data.local.mapper

import com.badront.pokedex.pokemon.core.data.local.model.ListPokemonEntity
import com.badront.pokedex.pokemon.core.domain.model.ListPokemon
import javax.inject.Inject

class ListPokemonEntityMapper @Inject constructor() {
    fun map(entity: ListPokemonEntity): ListPokemon {
        return ListPokemon(
            id = entity.id,
            name = entity.name,
            number = entity.number,
            image = entity.image
        )
    }

    fun map(model: ListPokemon): ListPokemonEntity {
        return ListPokemonEntity(
            id = model.id,
            name = model.name,
            image = model.image,
            number = model.number
        )
    }
}