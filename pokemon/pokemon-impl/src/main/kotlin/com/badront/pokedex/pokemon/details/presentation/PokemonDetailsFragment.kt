package com.badront.pokedex.pokemon.details.presentation

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import com.badront.pokedex.core.ext.android.content.getDimensionPixelOffsetKtx
import com.badront.pokedex.core.ext.android.view.setTint
import com.badront.pokedex.core.ext.androidx.palette.graphics.ColorPalette
import com.badront.pokedex.core.ext.androidx.palette.graphics.getPalette
import com.badront.pokedex.core.ext.kotlinx.coroutines.flow.observe
import com.badront.pokedex.core.model.LoadingState
import com.badront.pokedex.core.presentation.BaseFragment
import com.badront.pokedex.core.util.recycler.LinearSpacingItemDecoration
import com.badront.pokedex.pokemon.details.presentation.adapter.PokemonDetailsAdapter
import com.badront.pokedex.pokemon.details.presentation.model.DetailedPokemonUiModel
import com.badront.pokedex.pokemon.impl.R
import com.badront.pokedex.pokemon.impl.databinding.FrPokemonDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PokemonDetailsFragment : BaseFragment(R.layout.fr_pokemon_details) {
    @Inject
    internal lateinit var pokemonDetailsViewModelFactory: PokemonDetailsViewModelFactory
    private val parameters: PokemonDetailsParameters by lazy {
        PokemonDetailsFragmentArgs.fromBundle(requireArguments()).parameters
    }
    private val viewModel: PokemonDetailsViewModel by viewModels {
        PokemonDetailsViewModel.provideFactory(pokemonDetailsViewModelFactory, parameters)
    }

    private val viewBinding by viewBinding(FrPokemonDetailsBinding::bind)

    private val detailsAdapter by lazy {
        PokemonDetailsAdapter(
            onRetryClick = {
                viewModel.onEvent(PokemonDetailsViewModel.Event.ReloadPokemon)
            }
        )
    }
    private val spacingItemDecoration by lazy {
        LinearSpacingItemDecoration(
            orientation = RecyclerView.VERTICAL,
            betweenItems = requireContext().getDimensionPixelOffsetKtx(com.badront.pokedex.design.R.dimen.offset_16)
        )
    }

    private val defaultPalette by lazy {
        ColorPalette(
            primaryColor = requireContext().getColor(com.badront.pokedex.design.R.color.wireframe),
            onPrimaryColor = requireContext().getColor(com.badront.pokedex.design.R.color.white)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val transition = TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)
        sharedElementEnterTransition = transition
        sharedElementReturnTransition = transition
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (savedInstanceState == null) {
            postponeEnterTransition()
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewBinding.pokemonDetails) {
            adapter = detailsAdapter
            addItemDecoration(spacingItemDecoration)
        }
        viewBinding.loadingError.onRetryClickListener = {
            viewModel.onEvent(PokemonDetailsViewModel.Event.ReloadPokemon)
        }
    }

    override fun onDestroyView() {
        with(viewBinding.pokemonDetails) {
            removeItemDecoration(spacingItemDecoration)
        }
        super.onDestroyView()
    }

    override fun onObserveData() {
        super.onObserveData()
        viewModel.viewStates.observe(viewLifecycleOwner, ::bindViewState)
        viewModel.viewActions.observe(viewLifecycleOwner, ::bindViewActions)
    }

    private fun bindViewActions(action: PokemonDetailsViewModel.Action) {
        when (action) {
            is PokemonDetailsViewModel.Action.ShowError -> {
                Toast.makeText(requireContext(), action.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun bindViewState(state: DetailedPokemonUiModel) {
        when {
            state.header != null -> {
                viewBinding.pokemonContent.visibility = View.VISIBLE
                viewBinding.loadingProgress.visibility = View.GONE
                viewBinding.loadingError.visibility = View.GONE
                bindHeader(state.header)
            }
            state.loadingState == LoadingState.ERROR -> {
                viewBinding.pokemonContent.visibility = View.GONE
                viewBinding.loadingProgress.visibility = View.GONE
                viewBinding.loadingError.visibility = View.VISIBLE
            }
            state.loadingState == LoadingState.LOADING -> {
                viewBinding.pokemonContent.visibility = View.GONE
                viewBinding.loadingProgress.visibility = View.VISIBLE
                viewBinding.loadingError.visibility = View.GONE
            }
        }
        detailsAdapter.setItems(state.detailedList)
    }

    private fun bindHeader(header: DetailedPokemonUiModel.Header) {
        applyPalette(header)
        viewBinding.pokemonName.text = header.name
        viewBinding.pokemonNumber.text = header.number
        viewBinding.pokemonImage.transitionName = header.image
        viewBinding.pokemonImage.load(header.image) {
            placeholder(R.drawable.pokemon_egg)
            error(R.drawable.pokemon_empty)
            crossfade(true)
            listener(onError = { _, _ ->
                startPostponedEnterTransition()
            }) { _, _ ->
                startPostponedEnterTransition()
            }
            if (header.colorPalette == null) {
                getPalette { colorPalette ->
                    colorPalette?.let {
                        viewModel.onEvent(
                            PokemonDetailsViewModel.Event.PokemonColorPaletteReceived(it)
                        )
                    }
                }
            }
        }
    }

    private fun applyPalette(header: DetailedPokemonUiModel.Header) {
        val palette = header.colorPalette ?: defaultPalette
        with(palette) {
            viewBinding.pokemonImageHolder.setBackgroundColor(primaryColor)
            viewBinding.pokemonName.setTextColor(onPrimaryColor)
            viewBinding.pokemonNumber.setTextColor(onPrimaryColor)
            viewBinding.back.setTint(onPrimaryColor)
        }
    }
}