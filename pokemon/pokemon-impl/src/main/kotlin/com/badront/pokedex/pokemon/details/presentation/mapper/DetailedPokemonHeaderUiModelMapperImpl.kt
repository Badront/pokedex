package com.badront.pokedex.pokemon.details.presentation.mapper

import android.content.Context
import com.badront.pokedex.core.ext.androidx.palette.graphics.ColorPalette
import com.badront.pokedex.design.R
import com.badront.pokedex.pokemon.core.domain.model.Pokemon
import com.badront.pokedex.pokemon.details.presentation.model.DetailedPokemonUiModel
import com.badront.pokedex.pokemon.details.presentation.model.uiName
import com.badront.pokedex.pokemon.details.presentation.model.uiNumber
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

internal class DetailedPokemonHeaderUiModelMapperImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : DetailedPokemonHeaderUiModelMapper {
    override fun map(model: Pokemon, palette: ColorPalette?): DetailedPokemonUiModel.Header {
        return DetailedPokemonUiModel.Header(
            name = model.uiName,
            number = model.uiNumber,
            image = model.image,
            colorPalette = palette ?: ColorPalette(
                primaryColor = context.getColor(R.color.wireframe),
                onPrimaryColor = context.getColor(R.color.white)
            )
        )
    }
}