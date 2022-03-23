package com.badront.pokedex.pokemon.core.data.remote.mapper

import com.badront.pokedex.core.data.remote.model.NamedApiResourceDto
import com.badront.pokedex.pokemon.core.data.remote.model.PokemonTypeDto
import com.badront.pokedex.pokemon.core.domain.model.PokemonType
import javax.inject.Inject

class PokemonTypeDtoMapper @Inject constructor() {
    fun map(dto: PokemonTypeDto): PokemonType? {
        val type = map(dto.type)
        return if (type != null) {
            PokemonType(
                order = dto.slot,
                type = type
            )
        } else {
            null
        }
    }

    fun map(typeDto: NamedApiResourceDto): PokemonType.Type? {
        return when (typeDto.name) {
            "normal" -> PokemonType.Type.NORMAL
            "rock" -> PokemonType.Type.ROCK
            "ghost" -> PokemonType.Type.GHOST
            "steel" -> PokemonType.Type.STEEL
            "water" -> PokemonType.Type.WATER
            "grass" -> PokemonType.Type.GRASS
            "psychic" -> PokemonType.Type.PSYCHIC
            "ice" -> PokemonType.Type.ICE
            "dark" -> PokemonType.Type.DARK
            "fighting" -> PokemonType.Type.FIGHTING
            "flying" -> PokemonType.Type.FLYING
            "poison" -> PokemonType.Type.POISON
            "ground" -> PokemonType.Type.GROUND
            "bug" -> PokemonType.Type.BUG
            "fire" -> PokemonType.Type.FIRE
            "electric" -> PokemonType.Type.ELECTRIC
            "dragon" -> PokemonType.Type.DRAGON
            else -> null
        }
    }
}