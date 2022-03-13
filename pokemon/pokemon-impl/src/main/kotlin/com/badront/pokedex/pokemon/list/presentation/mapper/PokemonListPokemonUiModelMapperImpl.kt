package com.badront.pokedex.pokemon.list.presentation.mapper

import com.badront.pokedex.core.ext.androidx.palette.graphics.ColorPalette
import com.badront.pokedex.pokemon.core.domain.model.Pokemon
import com.badront.pokedex.pokemon.list.presentation.model.PokemonListUiModel
import java.util.Locale
import javax.inject.Inject

internal class PokemonListPokemonUiModelMapperImpl @Inject constructor() : PokemonListPokemonUiModelMapper {
    override fun map(model: Pokemon, palette: ColorPalette?): PokemonListUiModel.Pokemon {
        return PokemonListUiModel.Pokemon(
            id = model.id,
            number = "#${model.number.toString().padStart(4, '0')}",
            name = model.name.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
            image = model.image,
            palette = palette
        )
    }
}