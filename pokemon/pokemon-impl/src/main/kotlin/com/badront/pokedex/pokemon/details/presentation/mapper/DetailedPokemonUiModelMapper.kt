package com.badront.pokedex.pokemon.details.presentation.mapper

import com.badront.pokedex.pokemon.details.presentation.PokemonDetailsViewModel
import com.badront.pokedex.pokemon.details.presentation.model.DetailedPokemonUiModel

internal interface DetailedPokemonUiModelMapper {
    fun map(state: PokemonDetailsViewModel.State): DetailedPokemonUiModel
}