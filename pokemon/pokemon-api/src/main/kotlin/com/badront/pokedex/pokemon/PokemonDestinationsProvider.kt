package com.badront.pokedex.pokemon

import androidx.navigation.NavDirections
import com.badront.pokedex.pokemon.details.presentation.PokemonDetailsParameters

interface PokemonDestinationsProvider {
    fun getListDestination(): NavDirections
    fun getDetailsDestination(parameters: PokemonDetailsParameters): NavDirections
}