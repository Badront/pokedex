package com.badront.pokedex.evolution.widget.details

import android.view.ViewGroup
import by.kirich1409.viewbindingdelegate.viewBinding
import com.badront.pokedex.core.util.recycler.BaseViewHolder
import com.badront.pokedex.evolution.core.domain.model.EvolutionParam
import com.badront.pokedex.evolution.impl.R
import com.badront.pokedex.evolution.impl.databinding.LiEvolutionDetailPokemonSpeciesBinding
import com.badront.pokedex.pokemon.core.domain.model.Pokemon

class EvolutionTradeSpeciesViewHolder(
    parent: ViewGroup,
    onPokemonClick: (Pokemon) -> Unit
) :
    BaseViewHolder<EvolutionParam.TradeSpecies>(parent, R.layout.li_evolution_detail_pokemon_species) {
    private val binding: LiEvolutionDetailPokemonSpeciesBinding by viewBinding()

    init {
        binding.extraMessage.setText(R.string.evolution_trade_species)
        binding.pokemon.onPokemonClickListener = onPokemonClick
    }

    override fun bind(item: EvolutionParam.TradeSpecies) {
        super.bind(item)
        binding.pokemon.pokemon = item.pokemon
    }
}