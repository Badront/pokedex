package com.badront.pokedex.pokemon.details.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.badront.pokedex.core.presentation.BaseViewModel
import com.badront.pokedex.pokemon.core.domain.model.Pokemon
import com.badront.pokedex.pokemon.list.domain.GetPokemonById
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class PokemonDetailsViewModel @AssistedInject constructor(
    @Assisted private val parameters: PokemonDetailsParameters,
    private val getPokemonById: GetPokemonById
) : BaseViewModel<PokemonDetailsViewModel.State, Unit, Unit>() {
    init {
        subscribeForPokemon()
    }

    private fun subscribeForPokemon() {
        launch {
            val listPokemon = getPokemonById(parameters.id)
            viewState = State(listPokemon)
        }
    }

    data class State(
        val pokemon: Pokemon?
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
interface PokemonDetailsViewModelFactory {
    fun create(parameters: PokemonDetailsParameters): PokemonDetailsViewModel
}