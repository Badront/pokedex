package com.badront.pokedex.pokemon.list.presentation

import androidx.lifecycle.viewModelScope
import com.badront.pokedex.core.coroutines.AppDispatchers
import com.badront.pokedex.core.ext.androidx.palette.graphics.ColorPalette
import com.badront.pokedex.core.ext.kotlinx.coroutines.isCompletedOrCanceled
import com.badront.pokedex.core.model.LoadingState
import com.badront.pokedex.core.model.doOnFailure
import com.badront.pokedex.core.model.doOnSuccess
import com.badront.pokedex.core.presentation.BaseViewModel
import com.badront.pokedex.pokemon.core.domain.model.Pokemon
import com.badront.pokedex.pokemon.details.presentation.PokemonDetailsParameters
import com.badront.pokedex.pokemon.list.domain.GetListPokemonsAsFlow
import com.badront.pokedex.pokemon.list.domain.LoadAndSaveListPokemonsPage
import com.badront.pokedex.pokemon.list.presentation.mapper.PokemonListUiModelMapper
import com.badront.pokedex.pokemon.list.presentation.model.PokemonListUiModel
import com.badront.pokedex.pokemon.list.presentation.model.PokemonListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import java.util.Collections
import javax.inject.Inject

@HiltViewModel
internal class PokemonListViewModel @Inject constructor(
    private val appDispatchers: AppDispatchers,
    private val getListPokemonsAsFlow: GetListPokemonsAsFlow,
    private val loadAndSaveListPokemonsPage: LoadAndSaveListPokemonsPage,
    private val uiModelMapper: PokemonListUiModelMapper
) : BaseViewModel<PokemonListUiState, PokemonListViewModel.Action, PokemonListViewModel.Event>() {
    private var loadingJob: Job? = null
    private var totalCount: Int? = null
    private var stateFlow = MutableStateFlow(State(loadingState = LoadingState.LOADING))
    private val pokemonColorPalettes: MutableMap<Int, ColorPalette> = Collections.synchronizedMap(mutableMapOf())
    private var state: State
        get() = stateFlow.value
        set(value) {
            stateFlow.value = value
        }

    init {
        subscribeForCached()
        subscribeForState()
        loadPage(0)
    }

    private fun subscribeForCached() {
        getListPokemonsAsFlow()
            .flowOn(appDispatchers.io)
            .onEach { pokemons ->
                state = state.copy(pokemons = pokemons)
            }
            .launchIn(viewModelScope)
    }

    private fun subscribeForState() {
        stateFlow
            .map { state ->
                uiModelMapper.map(state, pokemonColorPalettes)
            }
            .flowOn(appDispatchers.computing)
            .onEach { uiState ->
                viewState = uiState
            }
            .launchIn(viewModelScope)
    }

    override fun onEvent(event: Event) {
        when (event) {
            is Event.PokemonColorPaletteReceived -> {
                pokemonColorPalettes[event.pokemon.id] = event.palette
            }
            is Event.OnPokemonClick -> {
                sendAction(Action.OpenPokemonDetails(event.position, PokemonDetailsParameters(event.pokemon.id)))
            }
            Event.ScrolledToNextPage -> {
                if (state.nextPageLoadingState == LoadingState.DATA && state.loadingState == LoadingState.DATA) {
                    scrollToNextPageIfNeeded()
                }
            }
            Event.Refresh -> {
                state = state.copy(isRefreshing = true)
                totalCount = null
                loadingJob?.cancel()
                loadPage(0)
            }
            Event.OnRetryLoadingClick -> {
                state = state.copy(loadingState = LoadingState.LOADING)
                loadingJob?.cancel()
                loadPage(0)
            }
            Event.OnRetryNextPageLoadingClick -> {
                scrollToNextPageIfNeeded()
            }
        }
    }

    private fun scrollToNextPageIfNeeded() {
        totalCount?.let { count ->
            val currentItemsCount = state.pokemons.count()
            if (currentItemsCount < count) {
                loadPage(currentItemsCount)
            }
        }
    }

    private fun loadPage(offset: Int) {
        if (loadingJob.isCompletedOrCanceled()) {
            val isFirstPage = offset == 0
            loadingJob = launch(onError = {
                onLoadingUnhandledError(it, isFirstPage)
            }) {
                state = state.copy(
                    nextPageLoadingState = if (isFirstPage) {
                        LoadingState.DATA
                    } else {
                        LoadingState.LOADING
                    }
                )
                loadAndSaveListPokemonsPage(offset = offset)
                    .doOnFailure {
                        onLoadingUnhandledError(it, isFirstPage)
                    }
                    .doOnSuccess { newItemsPage ->
                        totalCount = newItemsPage.total
                        if (state.isRefreshing) {
                            sendAction(Action.RefreshNextPageScroll)
                        }
                        state = state.copy(
                            isRefreshing = false,
                            loadingState = LoadingState.DATA,
                            nextPageLoadingState = LoadingState.DATA
                        )
                    }
            }
        }
    }

    private fun onLoadingUnhandledError(throwable: Throwable, isFirstPage: Boolean) {
        state = state.copy(
            isRefreshing = false,
            loadingState = if (isFirstPage && state.isRefreshing.not()) {
                LoadingState.ERROR
            } else {
                LoadingState.DATA
            },
            nextPageLoadingState = if (isFirstPage) {
                LoadingState.DATA
            } else {
                LoadingState.ERROR
            }
        )
        sendAction(Action.ShowError(throwable.localizedMessage))
    }

    fun isPageLoading(): Boolean {
        return loadingJob.isCompletedOrCanceled().not()
    }

    internal data class State(
        val isRefreshing: Boolean = false,
        val loadingState: LoadingState = LoadingState.LOADING,
        val nextPageLoadingState: LoadingState = LoadingState.DATA,
        val pokemons: List<Pokemon> = emptyList()
    )

    internal sealed class Event {
        class PokemonColorPaletteReceived(val pokemon: PokemonListUiModel.Pokemon, val palette: ColorPalette) : Event()
        class OnPokemonClick(val position: Int, val pokemon: PokemonListUiModel.Pokemon) : Event()
        object Refresh : Event()
        object OnRetryLoadingClick : Event()
        object ScrolledToNextPage : Event()
        object OnRetryNextPageLoadingClick : Event()
    }

    internal sealed class Action {
        class ShowError(val message: String?) : Action()
        class OpenPokemonDetails(val position: Int, val params: PokemonDetailsParameters) : Action()
        object RefreshNextPageScroll : Action()
    }
}