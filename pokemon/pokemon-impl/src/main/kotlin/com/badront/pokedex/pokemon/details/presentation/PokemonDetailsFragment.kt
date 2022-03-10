package com.badront.pokedex.pokemon.details.presentation

import androidx.fragment.app.viewModels
import com.badront.pokedex.core.presentation.BaseFragment
import com.badront.pokedex.pokemon.impl.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokemonDetailsFragment : BaseFragment(R.layout.fr_pokemon_details) {
    private val parameters: PokemonDetailsParameters by lazy {
        PokemonDetailsFragmentArgs.fromBundle(requireArguments()).parameters
    }
    private val viewModel: PokemonDetailsViewModel by viewModels()
    override fun onObserveData() {
        super.onObserveData()
    }
}