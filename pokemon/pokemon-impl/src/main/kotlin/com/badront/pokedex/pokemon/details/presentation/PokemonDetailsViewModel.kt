package com.badront.pokedex.pokemon.details.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.badront.pokedex.core.coroutines.AppDispatchers
import com.badront.pokedex.core.model.LoadingState
import com.badront.pokedex.core.presentation.BaseViewModel
import com.badront.pokedex.evolution.core.domain.model.EvolutionChain
import com.badront.pokedex.pokemon.core.domain.model.DetailedPokemon
import com.badront.pokedex.pokemon.core.domain.model.Pokemon
import com.badront.pokedex.pokemon.core.domain.usecase.GetPokemonDetailsByIdAsFlow
import com.badront.pokedex.pokemon.details.domain.LoadAndSaveDetailedPokemon
import com.badront.pokedex.pokemon.details.presentation.mapper.DetailedPokemonUiModelMapper
import com.badront.pokedex.pokemon.details.presentation.model.DetailedPokemonUiModel
import com.badront.pokedex.pokemon.evolution.domain.LoadPokemonEvolutionChain
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

internal class PokemonDetailsViewModel @AssistedInject constructor(
    @Assisted private val parameters: PokemonDetailsParameters,
    private val getPokemonDetailsByIdAsFlow: GetPokemonDetailsByIdAsFlow,
    private val loadAndSaveDetailedPokemon: LoadAndSaveDetailedPokemon,
    private val loadPokemonEvolutionChain: LoadPokemonEvolutionChain,
    private val detailedPokemonMapper: DetailedPokemonUiModelMapper,
    private val appDispatchers: AppDispatchers
) : BaseViewModel<DetailedPokemonUiModel, PokemonDetailsViewModel.Action, PokemonDetailsViewModel.Event>() {
    private var loadingStateFlow = MutableStateFlow(LoadingState.LOADING)
    private var evolutionFlow = MutableStateFlow<EvolutionChain?>(null)

    @Volatile
    private var state: State = State(LoadingState.LOADING)

    init {
        subscribeForState()
        loadPokemonDetails()
    }

    override fun onEvent(event: Event) {
        when (event) {
            Event.ReloadPokemon -> {
                loadingStateFlow.value = LoadingState.LOADING
                loadPokemonDetails()
            }
            is Event.EvolutionPokemonClick -> {
                if (event.pokemon.id != state.pokemon?.pokemon?.id) {
                    sendAction(Action.OpenPokemon(PokemonDetailsParameters(id = event.pokemon.id)))
                }
            }
        }
    }

    private fun loadPokemonDetails() {
        launch(
            onError = {
                onLoadingError(it)
                loadingStateFlow.value = LoadingState.ERROR
            }
        ) {
            /**
             * if pokemon loading finished with exception we need to stop all other scope coroutines
             */
            val pokemonDeferred = throwAsync { loadAndSaveDetailedPokemon(parameters.id) }
            val evolutionChainDeferred = async { loadPokemonEvolutionChain(parameters.id) }
            val pokemon = pokemonDeferred.await()
            loadingStateFlow.value = if (pokemon.isSuccess) {
                LoadingState.DATA
            } else {
                pokemon.exceptionOrNull()?.let { onLoadingError(it) }
                LoadingState.ERROR
            }
            val evolutionChain = evolutionChainDeferred.await()
            evolutionFlow.value = evolutionChain?.getOrNull()
        }
    }

    private fun onLoadingError(throwable: Throwable) {
        sendAction(Action.ShowError(throwable.localizedMessage))
    }

    private fun subscribeForState() {
        combine(
            loadingStateFlow,
            getPokemonDetailsByIdAsFlow(parameters.id),
            evolutionFlow
        ) { loading, details, evolutionChain ->
            State(loading, details, evolutionChain).also {
                state = it
            }
        }
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
        val evolutionChain: EvolutionChain? = null
    )

    internal sealed class Action {
        class ShowError(val message: String?) : Action()
        class OpenPokemon(val parameters: PokemonDetailsParameters) : Action()
    }

    internal sealed class Event {
        class EvolutionPokemonClick(val pokemon: Pokemon) : Event()
        object ReloadPokemon : Event()
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