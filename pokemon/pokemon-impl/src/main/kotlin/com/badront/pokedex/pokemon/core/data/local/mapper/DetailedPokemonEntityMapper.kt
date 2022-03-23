package com.badront.pokedex.pokemon.core.data.local.mapper

import com.badront.pokedex.pokemon.core.data.local.model.PokemonDetailsWithRelations
import com.badront.pokedex.pokemon.core.domain.model.PokemonDetails
import javax.inject.Inject

class DetailedPokemonEntityMapper @Inject constructor(
    private val detailsEntityMapper: PokemonDetailsEntityMapper,
    private val typeEntityMapper: PokemonTypeEntityMapper
) {
    fun map(entity: PokemonDetailsWithRelations): PokemonDetails {
        return detailsEntityMapper.map(entity.details, entity.types)
    }

    fun map(model: PokemonDetails): PokemonDetailsWithRelations {
        return PokemonDetailsWithRelations(
            details = detailsEntityMapper.map(model),
            types = model.types.map { typeEntityMapper.map(model.id, it) },
            stats = emptyList() // TODO add stats later
        )
    }
}