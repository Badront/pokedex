package com.badront.pokedex.pokemon

import android.os.Bundle
import androidx.navigation.NavDirections
import com.badront.pokedex.pokemon.details.presentation.PokemonDetailsParameters
import com.badront.pokedex.pokemon.impl.R
import com.badront.pokedex.pokemon.list.presentation.PokemonListFragmentDirections
import javax.inject.Inject

class PokemonDestinationsProviderImpl @Inject constructor() : PokemonDestinationsProvider {
    override fun getListDestination(): NavDirections {
        return object : NavDirections {
            override val actionId: Int = R.id.pokemonList
            override val arguments: Bundle = Bundle.EMPTY
        }
    }

    override fun getDetailsDestination(parameters: PokemonDetailsParameters): NavDirections {
        return PokemonListFragmentDirections.toDetails(parameters)
    }
}