package com.badront.pokedex.pokemon.core.domain.model

class PokemonType(
    val order: Int,
    val type: Type
) {
    enum class Type {
        NORMAL,
        ROCK,
        GHOST,
        STEEL,
        WATER,
        GRASS,
        PSYCHIC,
        ICE,
        DARK,
        FIGHTING,
        FLYING,
        POISON,
        GROUND,
        BUG,
        FIRE,
        ELECTRIC,
        DRAGON
    }
}