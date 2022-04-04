package com.badront.pokedex.evolution.widget.details

import android.view.ViewGroup
import by.kirich1409.viewbindingdelegate.viewBinding
import com.badront.pokedex.core.util.recycler.BaseViewHolder
import com.badront.pokedex.evolution.core.domain.model.EvolutionParam
import com.badront.pokedex.evolution.impl.R
import com.badront.pokedex.evolution.impl.databinding.LiEvolutionDetailTypeBinding

class EvolutionPartyTypeViewHolder(parent: ViewGroup) :
    BaseViewHolder<EvolutionParam.PartyType>(parent, R.layout.li_evolution_detail_type) {
    private val binding: LiEvolutionDetailTypeBinding by viewBinding()

    init {
        binding.extraMessage.setText(R.string.evolution_party_type)
    }

    override fun bind(item: EvolutionParam.PartyType) {
        super.bind(item)
        binding.type.type = item.pokemonType
    }
}