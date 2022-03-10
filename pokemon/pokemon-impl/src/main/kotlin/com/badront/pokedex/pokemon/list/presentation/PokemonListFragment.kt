package com.badront.pokedex.pokemon.list.presentation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.badront.pokedex.core.ext.kotlinx.coroutines.flow.observe
import com.badront.pokedex.core.presentation.BaseFragment
import com.badront.pokedex.pokemon.impl.R
import com.badront.pokedex.pokemon.impl.databinding.FrPokemonListBinding
import com.badront.pokedex.pokemon.list.presentation.adapter.PokemonListAdapter
import com.badront.pokedex.pokemon.list.presentation.model.PokemonListUiModel
import com.badront.pokedex.pokemon.list.presentation.model.PokemonListUiState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokemonListFragment : BaseFragment(R.layout.fr_pokemon_list) {
    private val viewBinding: FrPokemonListBinding by viewBinding()
    private val viewModel: PokemonListViewModel by viewModels()
    private val pokemonsAdapter by lazy {
        PokemonListAdapter(
            onPokemonClick = {
                viewModel.onEvent(PokemonListViewModel.Event.OnPokemonClick(it))
            },
            onNextPageLoadingRetryClick = {
                viewModel.onEvent(PokemonListViewModel.Event.OnRetryNextPageLoadingClick)
            }
        )
    }
    private val pokemonsLayoutManager by lazy {
        GridLayoutManager(requireContext(), TOTAL_SPAN_COUNT).apply {
            spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return when {
                        position >= pokemonsAdapter.itemCount -> DEFAULT_SPAN_SIZE
                        pokemonsAdapter.getItem(position) is PokemonListUiModel.Pokemon -> DEFAULT_SPAN_SIZE
                        else -> TOTAL_SPAN_COUNT
                    }
                }
            }
        }
    }
    private val spaceItemDecoration by lazy {
        PokemonGridSpacingItemDecoration(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewBinding.pokemons) {
            adapter = pokemonsAdapter
            layoutManager = pokemonsLayoutManager
            addItemDecoration(spaceItemDecoration)
        }
        viewBinding.refresh.setOnRefreshListener {
            viewModel.onEvent(PokemonListViewModel.Event.Refresh)
        }
    }

    override fun onDestroyView() {
        with(viewBinding.pokemons) {
            removeItemDecoration(spaceItemDecoration)
            layoutManager = null
            adapter = null
        }
        super.onDestroyView()
    }

    override fun onObserveData() {
        super.onObserveData()
        viewModel.viewStates.observe(viewLifecycleOwner) {
            if (it != null) {
                bindState(it)
            }
        }
        viewModel.viewActions.observe(viewLifecycleOwner) {
            bindAction(it)
        }
    }

    private fun bindAction(action: PokemonListViewModel.Action) {
        when (action) {
            is PokemonListViewModel.Action.OpenPokemonDetails -> {
                findNavController().navigate(PokemonListFragmentDirections.toDetails(action.params))
            }
            is PokemonListViewModel.Action.ShowError -> {
                Toast.makeText(requireContext(), action.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun bindState(state: PokemonListUiState) {
        pokemonsAdapter.setItems(state.items)
        viewBinding.refresh.isRefreshing = state.isRefreshing
        viewBinding.refresh.isVisible = state.isLoading.not()
        viewBinding.loadingProgress.isVisible = state.isLoading
    }

    private companion object {
        private const val TOTAL_SPAN_COUNT = 3
        private const val DEFAULT_SPAN_SIZE = 1
    }
}