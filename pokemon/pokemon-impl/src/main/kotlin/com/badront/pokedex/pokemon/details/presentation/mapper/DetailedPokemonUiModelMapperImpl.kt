package com.badront.pokedex.pokemon.details.presentation.mapper

import com.badront.pokedex.core.model.LoadingState
import com.badront.pokedex.pokemon.details.presentation.PokemonDetailsViewModel
import com.badront.pokedex.pokemon.details.presentation.model.DetailedPokemonUiModel
import com.badront.pokedex.pokemon.details.presentation.model.PokemonDetailsUiModel
import javax.inject.Inject

internal class DetailedPokemonUiModelMapperImpl @Inject constructor(
    private val headerUiMapper: DetailedPokemonHeaderUiModelMapper,
    private val pokemonDetailsUiMapper: PokemonDetailsUiModelMapper
) : DetailedPokemonUiModelMapper {
    override fun map(state: PokemonDetailsViewModel.State): DetailedPokemonUiModel {
        return DetailedPokemonUiModel(
            loadingState = state.loadingState,
            header = state.pokemon?.let { headerUiMapper.map(it.pokemon, state.palette) },
            detailedList = when (state.loadingState) {
                LoadingState.LOADING -> {
                    listOf(PokemonDetailsUiModel.Loading)
                }
                LoadingState.ERROR -> {
                    listOf(PokemonDetailsUiModel.LoadingError)
                }
                else -> {
                    state.pokemon?.details?.let { detailedPokemon ->
                        pokemonDetailsUiMapper.map(detailedPokemon, state.palette, state.evolutionChain)
                    } ?: emptyList()
                }
            }
        )
    }
}