package com.badront.pokedex.pokemon.details.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.badront.pokedex.core.coroutines.AppDispatchers
import com.badront.pokedex.core.ext.androidx.palette.graphics.ColorPalette
import com.badront.pokedex.core.model.LoadingState
import com.badront.pokedex.core.presentation.BaseViewModel
import com.badront.pokedex.pokemon.core.domain.model.DetailedPokemon
import com.badront.pokedex.pokemon.details.domain.GetPokemonDetailsByIdAsFlow
import com.badront.pokedex.pokemon.details.domain.LoadAndSaveDetailedPokemon
import com.badront.pokedex.pokemon.details.presentation.mapper.DetailedPokemonUiModelMapper
import com.badront.pokedex.pokemon.details.presentation.model.DetailedPokemonUiModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

internal class PokemonDetailsViewModel @AssistedInject constructor(
    @Assisted private val parameters: PokemonDetailsParameters,
    private val getPokemonDetailsByIdAsFlow: GetPokemonDetailsByIdAsFlow,
    private val loadAndSaveDetailedPokemon: LoadAndSaveDetailedPokemon,
    private val detailedPokemonMapper: DetailedPokemonUiModelMapper,
    private val appDispatchers: AppDispatchers
) : BaseViewModel<DetailedPokemonUiModel, PokemonDetailsViewModel.Action, PokemonDetailsViewModel.Event>() {
    private var stateFlow = MutableStateFlow(State(loadingState = LoadingState.LOADING, palette = parameters.palette))
    private var state: State
        get() = stateFlow.value
        set(value) {
            stateFlow.value = value
        }

    init {
        subscribeForPokemon()
        subscribeForState()
        loadPokemonDetails()
    }

    override fun onEvent(event: Event) {
        when (event) {
            is Event.PokemonColorPaletteReceived -> state = state.copy(palette = event.palette)
        }
    }

    private fun loadPokemonDetails() {
        launch(
            onError = {
                state = state.copy(loadingState = LoadingState.ERROR)
            }
        ) {
            val result = loadAndSaveDetailedPokemon(parameters.id)
            if (result.isSuccess) {
                state = state.copy(loadingState = LoadingState.DATA)
            } else {
                state = state.copy(loadingState = LoadingState.ERROR)
            }
        }
    }

    private fun subscribeForPokemon() {
        getPokemonDetailsByIdAsFlow(parameters.id)
            .onEach { detailedPokemon ->
                state = state.copy(pokemon = detailedPokemon)
            }
            .launchIn(viewModelScope)
    }

    private fun subscribeForState() {
        stateFlow
            .map { state ->
                detailedPokemonMapper.map(state)
            }
            .flowOn(appDispatchers.computing)
            .onEach { uiState ->
                viewState = uiState
            }
            .launchIn(viewModelScope)
    }

    internal data class State(
        val loadingState: LoadingState = LoadingState.LOADING,
        val pokemon: DetailedPokemon? = null,
        val palette: ColorPalette? = null
    )

    internal sealed class Action {
        class ShowError(val message: String?) : Action()
    }

    internal sealed class Event {
        class PokemonColorPaletteReceived(val palette: ColorPalette) : Event()
    }

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