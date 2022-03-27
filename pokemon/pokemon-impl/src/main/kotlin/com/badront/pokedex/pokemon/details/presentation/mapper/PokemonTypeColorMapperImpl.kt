package com.badront.pokedex.pokemon.details.presentation.mapper

import android.content.Context
import com.badront.pokedex.core.ext.android.content.getColorKtx
import com.badront.pokedex.design.R
import com.badront.pokedex.pokemon.core.domain.model.PokemonType
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

internal class PokemonTypeColorMapperImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : PokemonTypeColorMapper {
    override fun map(type: PokemonType.Type): Int {
        return context.getColorKtx(
            when (type) {
                PokemonType.Type.NORMAL -> R.color.type_normal
                PokemonType.Type.ROCK -> R.color.type_rock
                PokemonType.Type.GHOST -> R.color.type_ghost
                PokemonType.Type.STEEL -> R.color.type_steel
                PokemonType.Type.WATER -> R.color.type_water
                PokemonType.Type.GRASS -> R.color.type_grass
                PokemonType.Type.PSYCHIC -> R.color.type_psychic
                PokemonType.Type.ICE -> R.color.type_ice
                PokemonType.Type.DARK -> R.color.type_dark
                PokemonType.Type.FIGHTING -> R.color.type_fighting
                PokemonType.Type.FLYING -> R.color.type_flying
                PokemonType.Type.POISON -> R.color.type_poison
                PokemonType.Type.GROUND -> R.color.type_ground
                PokemonType.Type.BUG -> R.color.type_bug
                PokemonType.Type.FIRE -> R.color.type_fire
                PokemonType.Type.ELECTRIC -> R.color.type_electric
                PokemonType.Type.DRAGON -> R.color.type_dragon
            }
        )
    }
}