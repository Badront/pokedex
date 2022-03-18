package com.badront.pokedex.pokemon.details.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.badront.pokedex.core.model.LoadingState
import com.badront.pokedex.core.presentation.BaseViewModel
import com.badront.pokedex.pokemon.core.domain.model.DetailedPokemon
import com.badront.pokedex.pokemon.list.domain.GetPokemonById
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

internal class PokemonDetailsViewModel @AssistedInject constructor(
    @Assisted private val parameters: PokemonDetailsParameters,
    private val getPokemonById: GetPokemonById
) : BaseViewModel<PokemonDetailsViewModel.State, Unit, Unit>() {
    init {
        viewState = State(loadingState = LoadingState.LOADING)
        subscribeForPokemon()
        loadPokemonDetails()
    }

    private fun loadPokemonDetails() {
        launch(
            onError = {
                viewState = viewState.copy(loadingState = LoadingState.ERROR)
            }
        ) {

        }
    }

    private fun subscribeForPokemon() {
        launch {
            getPokemonById(parameters.id)?.let { listPokemon ->
                viewState = viewState.copy(pokemon = DetailedPokemon(pokemon = listPokemon))
            }
        }
    }

    internal data class State(
        val loadingState: LoadingState = LoadingState.LOADING,
        val pokemon: DetailedPokemon? = null
    )

    companion object {
        fun provideFactory(
            assistedFactory: PokemonDetailsViewModelFactory,
            parameters: PokemonDetailsParameters
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(parameters) as T
            }
        }
    }
}

@AssistedFactory
internal interface PokemonDetailsViewModelFactory {
    fun create(parameters: PokemonDetailsParameters): PokemonDetailsViewModel
}