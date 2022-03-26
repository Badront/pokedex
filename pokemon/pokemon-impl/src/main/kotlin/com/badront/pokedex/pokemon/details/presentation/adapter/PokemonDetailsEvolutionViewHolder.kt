package com.badront.pokedex.pokemon.details.presentation.adapter

import android.view.ViewGroup
import com.badront.pokedex.core.util.recycler.BaseViewHolder
import com.badront.pokedex.pokemon.details.presentation.model.PokemonDetailsUiModel
import com.badront.pokedex.pokemon.impl.R

class PokemonDetailsEvolutionViewHolder(parent: ViewGroup) :
    BaseViewHolder<PokemonDetailsUiModel.Evolution>(parent, R.layout.li_pokemon_details_evolution)