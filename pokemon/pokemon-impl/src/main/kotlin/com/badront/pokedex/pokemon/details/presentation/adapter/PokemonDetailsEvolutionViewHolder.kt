package com.badront.pokedex.pokemon.details.presentation.adapter

import android.view.ViewGroup
import by.kirich1409.viewbindingdelegate.viewBinding
import com.badront.pokedex.core.util.recycler.BaseViewHolder
import com.badront.pokedex.pokemon.details.presentation.model.PokemonDetailsUiModel
import com.badront.pokedex.pokemon.impl.R
import com.badront.pokedex.pokemon.impl.databinding.LiPokemonDetailsEvolutionBinding

class PokemonDetailsEvolutionViewHolder(parent: ViewGroup) :
    BaseViewHolder<PokemonDetailsUiModel.Evolution>(parent, R.layout.li_pokemon_details_evolution) {
    private val binding: LiPokemonDetailsEvolutionBinding by viewBinding()

    override fun bind(item: PokemonDetailsUiModel.Evolution) {
        super.bind(item)
        binding.evolution.evolution = item.chain
    }
}