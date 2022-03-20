package com.badront.pokedex.pokemon.details.presentation.mapper

import com.badront.pokedex.pokemon.details.presentation.PokemonDetailsViewModel
import com.badront.pokedex.pokemon.details.presentation.model.DetailedPokemonUiModel
import javax.inject.Inject

internal class DetailedPokemonUiModelMapperImpl @Inject constructor(
    private val headerUiMapper: DetailedPokemonHeaderUiModelMapper,
    private val pokemonDetailsUiMapper: PokemonDetailsUiModelMapper
) : DetailedPokemonUiModelMapper {
    override fun map(state: PokemonDetailsViewModel.State): DetailedPokemonUiModel {
        return DetailedPokemonUiModel(
            loadingState = state.loadingState,
            header = state.pokemon?.let { headerUiMapper.map(it.pokemon, state.palette) },
            detailedList = state.pokemon?.details?.let { detailedPokemon ->
                pokemonDetailsUiMapper.map(detailedPokemon, state.palette)
            } ?: emptyList()
        )
    }
}