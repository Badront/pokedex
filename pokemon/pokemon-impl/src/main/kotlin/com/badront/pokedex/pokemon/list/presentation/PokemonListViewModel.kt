package com.badront.pokedex.pokemon.list.presentation

import androidx.lifecycle.viewModelScope
import com.badront.pokedex.core.model.PageInfo
import com.badront.pokedex.core.model.fold
import com.badront.pokedex.core.presentation.BaseViewModel
import com.badront.pokedex.pokemon.core.domain.model.ListPokemon
import com.badront.pokedex.pokemon.core.domain.usecase.LoadListPokemonsPage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val loadListPokemonsPage: LoadListPokemonsPage
) : BaseViewModel<PokemonListViewModel.State, Unit, PokemonListViewModel.Action>() {
    private var totalCount: Long? = null

    init {
        viewState = State(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            viewState = State(isLoading = true)
            loadListPokemonsPage(PageInfo(100, 0))
                .fold(
                    onSuccess = {
                        totalCount = it.total
                        viewState = State(isLoading = false, items = it.items)
                    },
                    onFailure = {
                        viewState = State(isLoading = false)
                    }
                )
        }
    }

    data class State(
        val isLoading: Boolean,
        val items: List<ListPokemon> = emptyList()
    )

    sealed class Action {
        class OnPokemonClick(val pokemon: ListPokemon) : Action()
    }
}