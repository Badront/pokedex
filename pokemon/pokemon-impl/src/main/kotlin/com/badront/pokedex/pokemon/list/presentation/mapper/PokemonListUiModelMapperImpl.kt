package com.badront.pokedex.pokemon.list.presentation.mapper

import com.badront.pokedex.core.model.LoadingState
import com.badront.pokedex.pokemon.list.presentation.PokemonListViewModel
import com.badront.pokedex.pokemon.list.presentation.model.PokemonListUiModel
import com.badront.pokedex.pokemon.list.presentation.model.PokemonListUiState
import javax.inject.Inject

internal class PokemonListUiModelMapperImpl @Inject constructor(
    private val pokemonMapper: PokemonListPokemonUiModelMapper
) : PokemonListUiModelMapper {
    override fun map(state: PokemonListViewModel.State): PokemonListUiState {
        return PokemonListUiState(
            isRefreshing = state.isRefreshing,
            content = when (state.loadingState) {
                LoadingState.DATA -> {
                    val items = mutableListOf<PokemonListUiModel>()
                    items.addAll(state.items.map { listPokemon ->
                        pokemonMapper.map(listPokemon)
                    })
                    when (state.nextPageLoadingState) {
                        LoadingState.LOADING -> items.add(PokemonListUiModel.NextPageLoading)
                        LoadingState.ERROR -> items.add(PokemonListUiModel.NextPageLoadingError)
                    }
                    PokemonListUiState.Content.Data(items)
                }
                LoadingState.LOADING -> PokemonListUiState.Content.Loading
                LoadingState.ERROR -> PokemonListUiState.Content.Error
            }
        )
    }
}