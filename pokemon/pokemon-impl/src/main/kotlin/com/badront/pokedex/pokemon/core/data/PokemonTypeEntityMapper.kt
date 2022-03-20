package com.badront.pokedex.pokemon.core.data

import com.badront.pokedex.pokemon.core.data.local.model.PokemonTypeEntity
import com.badront.pokedex.pokemon.core.domain.model.PokeId
import com.badront.pokedex.pokemon.core.domain.model.PokemonType
import javax.inject.Inject

class PokemonTypeEntityMapper @Inject constructor() {
    fun map(pokemonId: PokeId, model: PokemonType): PokemonTypeEntity {
        return PokemonTypeEntity(
            pokemonId = pokemonId,
            type = model.type.name,
            order = model.order
        )
    }

    fun map(entity: PokemonTypeEntity): PokemonType {
        return PokemonType(
            order = entity.order,
            type = PokemonType.Type.valueOf(entity.type)
        )
    }
}