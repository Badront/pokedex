package com.badront.pokedex.pokemon.details.presentation

import android.os.Bundle
import android.view.View
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onObserveData() {
        super.onObserveData()
        viewModel.viewStates.observe(viewLifecycleOwner) { state ->
            state?.let { bindViewState(it) }
        }
    }

    private fun bindViewState(state: PokemonDetailsViewModel.State) {
        viewBinding.pokemonImage.load(state.pokemon?.image) {
            placeholder(R.drawable.egg)
            error(R.drawable.empty)
            crossfade(true)
        }
    }
}