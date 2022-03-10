package com.badront.pokedex.pokemon.list.presentation.mapper

import com.badront.pokedex.pokemon.list.presentation.PokemonListViewModel
import com.badront.pokedex.pokemon.list.presentation.model.PokemonListUiModel
import com.badront.pokedex.pokemon.list.presentation.model.PokemonListUiState
import javax.inject.Inject

internal class PokemonListUiModelMapperImpl @Inject constructor(
    private val pokemonMapper: PokemonListPokemonUiModelMapper
) : PokemonListUiModelMapper {
    override fun map(state: PokemonListViewModel.State): PokemonListUiState {
        val items = mutableListOf<PokemonListUiModel>()
        items.addAll(state.items.map { listPokemon ->
            pokemonMapper.map(listPokemon)
        })
        when (state.nextPageLoadingState) {
            PokemonListViewModel.NextPageLoadingState.LOADING -> items.add(PokemonListUiModel.NextPageLoading)
            PokemonListViewModel.NextPageLoadingState.ERROR -> items.add(PokemonListUiModel.NextPageLoadingError)
        }
        return PokemonListUiState(
            isRefreshing = state.isRefreshing,
            isLoading = state.isLoading,
            items = items
        )
    }
}