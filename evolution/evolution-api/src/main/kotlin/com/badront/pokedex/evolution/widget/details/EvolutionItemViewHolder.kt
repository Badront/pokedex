package com.badront.pokedex.evolution.widget.details

import android.view.ViewGroup
import by.kirich1409.viewbindingdelegate.viewBinding
import com.badront.pokedex.core.util.recycler.BaseViewHolder
import com.badront.pokedex.evolution.core.domain.model.EvolutionParam
import com.badront.pokedex.evolution.impl.R
import com.badront.pokedex.evolution.impl.databinding.LiEvolutionDetailItemBinding
import com.badront.pokedex.item.core.domain.model.Item

internal class EvolutionItemViewHolder(
    parent: ViewGroup,
    onItemCLick: (Item) -> Unit
) :
    BaseViewHolder<EvolutionParam.ItemParam>(parent, R.layout.li_evolution_detail_item) {
    private val binding: LiEvolutionDetailItemBinding by viewBinding()

    init {
        binding.item.onItemClickListener = onItemCLick
    }

    override fun bind(item: EvolutionParam.ItemParam) {
        super.bind(item)
        binding.item.item = item.item
    }
}