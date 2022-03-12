package com.badront.pokedex.pokemon.details.presentation

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import com.badront.pokedex.core.ext.kotlinx.coroutines.flow.observe
import com.badront.pokedex.core.presentation.BaseFragment
import com.badront.pokedex.pokemon.impl.R
import com.badront.pokedex.pokemon.impl.databinding.FrPokemonDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PokemonDetailsFragment : BaseFragment(R.layout.fr_pokemon_details) {
    @Inject
    lateinit var pokemonDetailsViewModelFactory: PokemonDetailsViewModelFactory
    private val parameters: PokemonDetailsParameters by lazy {
        PokemonDetailsFragmentArgs.fromBundle(requireArguments()).parameters
    }
    private val viewModel: PokemonDetailsViewModel by viewModels {
        PokemonDetailsViewModel.provideFactory(pokemonDetailsViewModelFactory, parameters)
    }

    private val viewBinding by viewBinding(FrPokemonDetailsBinding::bind)

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

    override fun onObserveData() {
        super.onObserveData()
        viewModel.viewStates.observe(viewLifecycleOwner) { state ->
            state?.let { bindViewState(it) }
        }
    }

    private fun bindViewState(state: PokemonDetailsViewModel.State) {
        state.pokemon?.let { pokemon ->
            viewBinding.pokemonImage.transitionName = pokemon.image
            viewBinding.pokemonImage.load(pokemon.image) {
                placeholder(R.drawable.egg)
                error(R.drawable.empty)
                crossfade(true)
                listener(onError = { _, _ ->
                    startPostponedEnterTransition()
                }) { _, _ ->
                    startPostponedEnterTransition()
                }
            }
        }
    }
}