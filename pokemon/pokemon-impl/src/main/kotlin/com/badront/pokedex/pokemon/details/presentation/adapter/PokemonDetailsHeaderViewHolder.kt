package com.badront.pokedex.pokemon.details.presentation.adapter

import android.view.ViewGroup
import by.kirich1409.viewbindingdelegate.viewBinding
import com.badront.pokedex.core.ext.model.setText
import com.badront.pokedex.core.util.recycler.BaseViewHolder
import com.badront.pokedex.pokemon.details.presentation.model.PokemonDetailsUiModel
import com.badront.pokedex.pokemon.impl.R
import com.badront.pokedex.pokemon.impl.databinding.LiPokemonDetailsHeaderBinding

class PokemonDetailsHeaderViewHolder(parent: ViewGroup) :
    BaseViewHolder<PokemonDetailsUiModel.Header>(parent, R.layout.li_pokemon_details_header) {
    private val viewBinding by viewBinding<LiPokemonDetailsHeaderBinding>()
    override fun bind(item: PokemonDetailsUiModel.Header) {
        super.bind(item)
        with(viewBinding.headerText) {
            setText(item.text)
        }
    }
}