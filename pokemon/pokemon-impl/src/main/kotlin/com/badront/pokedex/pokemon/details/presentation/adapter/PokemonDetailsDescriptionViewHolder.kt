package com.badront.pokedex.pokemon.details.presentation.adapter

import android.view.ViewGroup
import by.kirich1409.viewbindingdelegate.viewBinding
import com.badront.pokedex.core.util.recycler.BaseViewHolder
import com.badront.pokedex.pokemon.details.presentation.model.PokemonDetailsUiModel
import com.badront.pokedex.pokemon.impl.R
import com.badront.pokedex.pokemon.impl.databinding.LiPokemonDetailsDescriptionBinding

class PokemonDetailsDescriptionViewHolder(parent: ViewGroup) :
    BaseViewHolder<PokemonDetailsUiModel.Description>(parent, R.layout.li_pokemon_details_description) {
    private val viewBinding by viewBinding<LiPokemonDetailsDescriptionBinding>()
    override fun bind(item: PokemonDetailsUiModel.Description) {
        super.bind(item)
        viewBinding.description.text = item.text
    }
}