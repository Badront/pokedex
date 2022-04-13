package com.badront.pokedex.pokemon.core.widget

import androidx.annotation.ColorRes
import com.badront.pokedex.pokemon.core.domain.model.PokemonType

@ColorRes
fun PokemonType.Type.getColorResId(): Int {
    return when (this) {
        PokemonType.Type.NORMAL -> com.badront.pokedex.design.R.color.type_normal
        PokemonType.Type.ROCK -> com.badront.pokedex.design.R.color.type_rock
        PokemonType.Type.GHOST -> com.badront.pokedex.design.R.color.type_ghost
        PokemonType.Type.STEEL -> com.badront.pokedex.design.R.color.type_steel
        PokemonType.Type.WATER -> com.badront.pokedex.design.R.color.type_water
        PokemonType.Type.GRASS -> com.badront.pokedex.design.R.color.type_grass
        PokemonType.Type.PSYCHIC -> com.badront.pokedex.design.R.color.type_psychic
        PokemonType.Type.ICE -> com.badront.pokedex.design.R.color.type_ice
        PokemonType.Type.DARK -> com.badront.pokedex.design.R.color.type_dark
        PokemonType.Type.FIGHTING -> com.badront.pokedex.design.R.color.type_fighting
        PokemonType.Type.FLYING -> com.badront.pokedex.design.R.color.type_flying
        PokemonType.Type.POISON -> com.badront.pokedex.design.R.color.type_poison
        PokemonType.Type.GROUND -> com.badront.pokedex.design.R.color.type_ground
        PokemonType.Type.BUG -> com.badront.pokedex.design.R.color.type_bug
        PokemonType.Type.FIRE -> com.badront.pokedex.design.R.color.type_fire
        PokemonType.Type.ELECTRIC -> com.badront.pokedex.design.R.color.type_electric
        PokemonType.Type.DRAGON -> com.badront.pokedex.design.R.color.type_dragon
        PokemonType.Type.FAIRY -> com.badront.pokedex.design.R.color.type_fairy
    }
}