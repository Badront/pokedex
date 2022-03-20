package com.badront.pokedex.pokemon.details.presentation.adapter

import android.view.ViewGroup
import androidx.core.view.isVisible
import by.kirich1409.viewbindingdelegate.viewBinding
import com.badront.pokedex.core.model.setText
import com.badront.pokedex.core.util.recycler.BaseViewHolder
import com.badront.pokedex.pokemon.details.presentation.model.PokemonDetailsUiModel
import com.badront.pokedex.pokemon.impl.R
import com.badront.pokedex.pokemon.impl.databinding.LiPokemonDetailsMeasurementsBinding

class PokemonDetailsMeasurementsViewHolder(parent: ViewGroup) :
    BaseViewHolder<PokemonDetailsUiModel.Measurements>(parent, R.layout.li_pokemon_details_measurements) {
    private val viewBinding by viewBinding<LiPokemonDetailsMeasurementsBinding>()

    override fun bind(item: PokemonDetailsUiModel.Measurements) {
        super.bind(item)
        viewBinding.weightGroup.isVisible = item.weight != null
        viewBinding.weight.setText(item.weight)
        viewBinding.heightGroup.isVisible = item.height != null
        viewBinding.height.setText(item.height)
    }
}