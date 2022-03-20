package com.badront.pokedex.pokemon.core.data

import com.badront.pokedex.core.model.measurements.Height
import com.badront.pokedex.core.model.measurements.Weight
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
            weight = model.weight?.value,
            weightUOM = model.weight?.uom,
            height = model.height?.value,
            heightUOM = model.height?.uom
        )
    }

    fun map(entity: PokemonDetailsEntity, typeEntities: List<PokemonTypeEntity>): PokemonDetails {
        return PokemonDetails(
            id = entity.id,
            height = if (entity.height != null && entity.heightUOM != null) {
                Height(
                    entity.height,
                    entity.heightUOM
                )
            } else {
                null
            },
            weight = if (entity.weight != null && entity.weightUOM != null) {
                Weight(
                    entity.weight,
                    entity.weightUOM
                )
            } else {
                null
            },
            types = typeEntities.map { typeEntityMapper.map(it) }
        )
    }
}