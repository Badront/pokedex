package com.badront.pokedex.pokemon.details.presentation

import android.os.Parcelable
import com.badront.pokedex.core.ext.androidx.palette.graphics.ColorPalette
import com.badront.pokedex.pokemon.core.domain.model.PokeId
import kotlinx.parcelize.Parcelize

@Parcelize
class PokemonDetailsParameters(
    val id: PokeId,
    val palette: ColorPalette? = null
) : Parcelable