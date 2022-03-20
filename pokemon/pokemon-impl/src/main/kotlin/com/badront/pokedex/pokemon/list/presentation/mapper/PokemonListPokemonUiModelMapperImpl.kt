package com.badront.pokedex.pokemon.list.presentation.mapper

import com.badront.pokedex.core.ext.androidx.palette.graphics.ColorPalette
import com.badront.pokedex.pokemon.core.domain.model.Pokemon
import com.badront.pokedex.pokemon.details.presentation.model.uiName
import com.badront.pokedex.pokemon.details.presentation.model.uiNumber
import com.badront.pokedex.pokemon.list.presentation.model.PokemonListUiModel
import javax.inject.Inject

internal class PokemonListPokemonUiModelMapperImpl @Inject constructor() : PokemonListPokemonUiModelMapper {
    override fun map(model: Pokemon, palette: ColorPalette?): PokemonListUiModel.Pokemon {
        return PokemonListUiModel.Pokemon(
            id = model.id,
            number = model.uiNumber,
            name = model.uiName,
            image = model.image,
            palette = palette
        )
    }
}