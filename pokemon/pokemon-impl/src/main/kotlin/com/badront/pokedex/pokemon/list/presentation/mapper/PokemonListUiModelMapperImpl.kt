package com.badront.pokedex.pokemon.list.presentation.mapper

import com.badront.pokedex.core.model.LoadingState
import com.badront.pokedex.pokemon.list.presentation.PokemonListViewModel
import com.badront.pokedex.pokemon.list.presentation.model.PokemonListUiModel
import com.badront.pokedex.pokemon.list.presentation.model.PokemonListUiState
import javax.inject.Inject

internal class PokemonListUiModelMapperImpl @Inject constructor(
    private val pokemonMapper: PokemonListPokemonUiModelMapper
) : PokemonListUiModelMapper {
    override fun map(
        state: PokemonListViewModel.State
    ): PokemonListUiState {
        return PokemonListUiState(
            isRefreshing = state.isRefreshing,
            content = when {
                state.loadingState == LoadingState.DATA ||
                    (state.loadingState == LoadingState.ERROR && state.pokemons.isNotEmpty()) -> {
                    val items = mutableListOf<PokemonListUiModel>()
                    items.addAll(state.pokemons.map { listPokemon ->
                        pokemonMapper.map(listPokemon)
                    })
                    when (state.nextPageLoadingState) {
                        LoadingState.LOADING -> items.add(PokemonListUiModel.NextPageLoading)
                        LoadingState.ERROR -> items.add(PokemonListUiModel.NextPageLoadingError)
                        else -> {
                            // ignore
                        }
                    }
                    PokemonListUiState.Content.Data(items)
                }
                state.loadingState == LoadingState.LOADING -> PokemonListUiState.Content.Loading
                state.loadingState == LoadingState.ERROR -> PokemonListUiState.Content.Error
                else -> PokemonListUiState.Content.Data(emptyList())
            }
        )
    }
}