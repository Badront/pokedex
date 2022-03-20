package com.badront.pokedex.pokemon.core.data

import com.badront.pokedex.pokemon.core.data.local.model.PokemonDetailsEntity
import com.badront.pokedex.pokemon.core.data.local.model.PokemonTypeEntity
import com.badront.pokedex.pokemon.core.domain.model.PokemonDetails
import javax.inject.Inject

class PokemonDetailsEntityMapper @Inject constructor(
    private val typeEntityMapper: PokemonTypeEntityMapper
) {
    fun map(model: PokemonDetails): PokemonDetailsEntity {
        return PokemonDetailsEntity(
            id = model.id,
            weight = model.weight,
            height = model.height
        )
    }

    fun map(entity: PokemonDetailsEntity, typeEntities: List<PokemonTypeEntity>): PokemonDetails {
        return PokemonDetails(
            id = entity.id,
            height = entity.height,
            weight = entity.weight,
            types = typeEntities.map { typeEntityMapper.map(it) }
        )
    }
}