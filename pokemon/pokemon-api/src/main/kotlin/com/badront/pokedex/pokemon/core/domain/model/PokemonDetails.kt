package com.badront.pokedex.pokemon.core.domain.model

import com.badront.pokedex.core.model.measurements.Height
import com.badront.pokedex.core.model.measurements.Weight

class PokemonDetails(
    val id: PokeId,
    val height: Height?,
    val weight: Weight?,
    val types: List<PokemonType>
)