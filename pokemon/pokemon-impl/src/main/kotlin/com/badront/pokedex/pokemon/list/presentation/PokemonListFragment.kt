package com.badront.pokedex.pokemon.list.presentation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.app.SharedElementCallback
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.badront.pokedex.core.ext.kotlinx.coroutines.flow.observe
import com.badront.pokedex.core.presentation.BaseFragment
import com.badront.pokedex.core.util.recycler.PageScrollListener
import com.badront.pokedex.pokemon.impl.R
import com.badront.pokedex.pokemon.impl.databinding.FrPokemonListBinding
import com.badront.pokedex.pokemon.list.presentation.adapter.PokemonItemViewHolder
import com.badront.pokedex.pokemon.list.presentation.adapter.PokemonListAdapter
import com.badront.pokedex.pokemon.list.presentation.model.PokemonListUiModel
import com.badront.pokedex.pokemon.list.presentation.model.PokemonListUiState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokemonListFragment : BaseFragment(R.layout.fr_pokemon_list) {
    private val viewBinding by viewBinding(FrPokemonListBinding::bind)
    private val viewModel: PokemonListViewModel by viewModels()
    private val pokemonsAdapter by lazy {
        PokemonListAdapter(
            onPokemonClick = { position, pokemon ->
                viewModel.onEvent(PokemonListViewModel.Event.OnPokemonClick(position, pokemon))
            },
            onPokemonPaletteLoaded = { pokemon, palette ->
                viewModel.onEvent(PokemonListViewModel.Event.PokemonColorPaletteReceived(pokemon, palette))
            },
            onNextPageLoadingRetryClick = {
                viewModel.onEvent(PokemonListViewModel.Event.OnRetryNextPageLoadingClick)
            }
        )
    }
    private val pokemonsLayoutManager by lazy {
        object : GridLayoutManager(requireContext(), TOTAL_SPAN_COUNT) {
            override fun onLayoutCompleted(state: RecyclerView.State) {
                super.onLayoutCompleted(state)
                startPostponedEnterTransition()
            }
        }.apply {
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
    private val nextPageScrollListener: PageScrollListener = object : PageScrollListener() {
        override val itemsBeforeLoad: Int = ITEMS_BEFORE_NEXT_PAGE_LOAD

        override fun isPageLoading(): Boolean = viewModel.isPageLoading()

        override fun loadNextPage() {
            viewModel.onEvent(PokemonListViewModel.Event.ScrolledToNextPage)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareTransitions()
        with(viewBinding.pokemons) {
            adapter = pokemonsAdapter
            layoutManager = pokemonsLayoutManager
            addItemDecoration(spaceItemDecoration)
            addOnScrollListener(nextPageScrollListener)
        }
        viewBinding.refresh.setOnRefreshListener {
            viewModel.onEvent(PokemonListViewModel.Event.Refresh)
        }
        viewBinding.loadingError.setOnClickListener {
            viewModel.onEvent(PokemonListViewModel.Event.OnRetryLoadingClick)
        }
    }

    override fun onDestroyView() {
        with(viewBinding.pokemons) {
            removeOnScrollListener(nextPageScrollListener)
            removeItemDecoration(spaceItemDecoration)
            layoutManager = null
            adapter = null
        }
        super.onDestroyView()
    }

    override fun onObserveData() {
        super.onObserveData()
        viewModel.viewStates.observe(viewLifecycleOwner, ::bindState)
        viewModel.viewActions.observe(viewLifecycleOwner, ::bindAction)
    }

    private fun bindAction(action: PokemonListViewModel.Action) {
        when (action) {
            is PokemonListViewModel.Action.OpenPokemonDetails -> {
                val viewHolder =
                    viewBinding.pokemons.findViewHolderForAdapterPosition(action.position) as PokemonItemViewHolder
                findNavController().navigate(
                    directions = PokemonListFragmentDirections.toDetails(action.params),
                    navigatorExtras = FragmentNavigatorExtras(
                        viewHolder.pokemonImage to viewHolder.pokemonImage.transitionName
                    )
                )
            }
            is PokemonListViewModel.Action.ShowError -> {
                Toast.makeText(requireContext(), action.message, Toast.LENGTH_SHORT).show()
            }
            PokemonListViewModel.Action.RefreshNextPageScroll -> {
                nextPageScrollListener.refresh()
            }
        }
    }

    private fun bindState(state: PokemonListUiState) {
        viewBinding.refresh.isRefreshing = state.isRefreshing
        when (state.content) {
            is PokemonListUiState.Content.Data -> {
                viewBinding.refresh.isVisible = true
                viewBinding.loadingProgress.isVisible = false
                viewBinding.loadingError.isVisible = false
                pokemonsAdapter.setItems(state.content.items)
            }
            PokemonListUiState.Content.Error -> {
                viewBinding.refresh.isVisible = false
                viewBinding.loadingProgress.isVisible = false
                viewBinding.loadingError.isVisible = true
            }
            PokemonListUiState.Content.Loading -> {
                viewBinding.refresh.isVisible = false
                viewBinding.loadingProgress.isVisible = true
                viewBinding.loadingError.isVisible = false
            }
        }
    }

    private fun prepareTransitions() {
        postponeEnterTransition()
        setExitSharedElementCallback(object : SharedElementCallback() {
            override fun onMapSharedElements(names: MutableList<String>, sharedElements: MutableMap<String, View>) {
                super.onMapSharedElements(names, sharedElements)
                if (sharedElements.isEmpty()) {
                    names.forEach { sharedName ->
                        sharedElements[sharedName] = viewBinding.pokemons.findViewWithTag(sharedName)
                    }
                }
            }
        })
    }

    private companion object {
        private const val TOTAL_SPAN_COUNT = 3
        private const val DEFAULT_SPAN_SIZE = 1

        private const val ITEMS_BEFORE_NEXT_PAGE_LOAD = 5
    }
}