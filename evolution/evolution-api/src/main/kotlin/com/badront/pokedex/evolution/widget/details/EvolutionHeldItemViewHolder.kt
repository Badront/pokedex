package com.badront.pokedex.evolution.widget.details

import android.view.ViewGroup
import by.kirich1409.viewbindingdelegate.viewBinding
import com.badront.pokedex.core.util.recycler.BaseViewHolder
import com.badront.pokedex.evolution.core.domain.model.EvolutionParam
import com.badront.pokedex.evolution.impl.R
import com.badront.pokedex.evolution.impl.databinding.LiEvolutionDetailHeldItemBinding
import com.badront.pokedex.item.core.domain.model.Item

class EvolutionHeldItemViewHolder(
    parent: ViewGroup,
    onItemClick: (Item) -> Unit
) :
    BaseViewHolder<EvolutionParam.HeldItemParam>(parent, R.layout.li_evolution_detail_held_item) {
    private val binding: LiEvolutionDetailHeldItemBinding by viewBinding()

    init {
        binding.item.onItemClickListener = onItemClick
    }

    override fun bind(item: EvolutionParam.HeldItemParam) {
        super.bind(item)
        binding.item.item = item.item
    }
}