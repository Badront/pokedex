package com.badront.pokedex.pokemon.details.presentation.mapper

import com.badront.pokedex.pokemon.core.domain.model.Pokemon
import com.badront.pokedex.pokemon.details.presentation.model.DetailedPokemonUiModel
import com.badront.pokedex.pokemon.details.presentation.model.uiName
import com.badront.pokedex.pokemon.details.presentation.model.uiNumber
import javax.inject.Inject

internal class DetailedPokemonHeaderUiModelMapperImpl @Inject constructor() : DetailedPokemonHeaderUiModelMapper {
    override fun map(model: Pokemon): DetailedPokemonUiModel.Header {
        return DetailedPokemonUiModel.Header(
            name = model.uiName,
            number = model.uiNumber,
            image = model.image
        )
    }
}