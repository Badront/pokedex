package com.badront.pokedex.pokemon.details.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.badront.pokedex.core.presentation.BaseFragment
import com.badront.pokedex.pokemon.impl.R
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onObserveData() {
        super.onObserveData()
    }
}