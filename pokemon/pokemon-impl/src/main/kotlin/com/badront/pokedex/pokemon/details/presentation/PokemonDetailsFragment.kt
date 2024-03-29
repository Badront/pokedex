package com.badront.pokedex.pokemon.details.presentation

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.badront.pokedex.core.ext.android.content.getDimensionPixelOffsetKtx
import com.badront.pokedex.core.ext.android.view.setTint
import com.badront.pokedex.core.ext.androidx.palette.graphics.ColorPalette
import com.badront.pokedex.core.ext.androidx.palette.graphics.getPalette
import com.badront.pokedex.core.ext.kotlin.lazyUnsynchronized
import com.badront.pokedex.core.ext.kotlinx.coroutines.flow.observe
import com.badront.pokedex.core.model.LoadingState
import com.badront.pokedex.core.presentation.BaseFragment
import com.badront.pokedex.core.util.recycler.LinearSpacingItemDecoration
import com.badront.pokedex.pokemon.core.widget.loadPokemon
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
    private val parameters: PokemonDetailsParameters by lazyUnsynchronized {
        PokemonDetailsFragmentArgs.fromBundle(requireArguments()).parameters
    }
    private val viewModel: PokemonDetailsViewModel by viewModels {
        PokemonDetailsViewModel.provideFactory(pokemonDetailsViewModelFactory, parameters)
    }

    private val viewBinding by viewBinding(FrPokemonDetailsBinding::bind)

    private val detailsAdapter by lazyUnsynchronized {
        PokemonDetailsAdapter(
            onPokemonClick = {
                viewModel.onEvent(PokemonDetailsViewModel.Event.EvolutionPokemonClick(it))
            },
            onRetryClick = {
                viewModel.onEvent(PokemonDetailsViewModel.Event.ReloadPokemon)
            }
        )
    }
    private val spacingItemDecoration by lazyUnsynchronized {
        val verticalSidesPadding =
            requireContext().getDimensionPixelOffsetKtx(com.badront.pokedex.design.R.dimen.offset_8)
        LinearSpacingItemDecoration(
            orientation = RecyclerView.VERTICAL,
            betweenItems = requireContext().getDimensionPixelOffsetKtx(com.badront.pokedex.design.R.dimen.offset_16),
            paddingTop = verticalSidesPadding,
            paddingBottom = verticalSidesPadding
        )
    }

    private val defaultPalette by lazyUnsynchronized {
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
        viewBinding.back.setOnClickListener {
            findNavController().popBackStack()
        }
        applyPalette(defaultPalette)
    }

    override fun onDestroyView() {
        with(viewBinding.pokemonDetails) {
            removeItemDecoration(spacingItemDecoration)
            adapter = null
        }
        viewBinding.loadingError.onRetryClickListener = null
        viewBinding.back.setOnClickListener(null)
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
            is PokemonDetailsViewModel.Action.OpenPokemon -> {
                findNavController().navigate(
                    directions = PokemonDetailsFragmentDirections.actionPokemonDetailsSelf(action.parameters)
                )
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
        viewBinding.pokemonName.text = header.name
        viewBinding.pokemonNumber.text = header.number
        viewBinding.pokemonImage.transitionName = header.image
        viewBinding.pokemonImage.loadPokemon(header.image) {
            allowHardware(false)
            listener(
                onError = { _, _ ->
                    startPostponedEnterTransition()
                },
                onSuccess = { _, result ->
                    startPostponedEnterTransition()
                    result.getPalette { colorPalette ->
                        colorPalette?.let {
                            applyPalette(it)
                        }
                    }
                }
            )
        }
    }

    private fun applyPalette(palette: ColorPalette) {
        with(palette) {
            viewBinding.pokemonImageHolder.setBackgroundColor(primaryColor)
            viewBinding.pokemonName.setTextColor(onPrimaryColor)
            viewBinding.pokemonNumber.setTextColor(onPrimaryColor)
            viewBinding.back.setTint(onPrimaryColor)
        }
    }
}