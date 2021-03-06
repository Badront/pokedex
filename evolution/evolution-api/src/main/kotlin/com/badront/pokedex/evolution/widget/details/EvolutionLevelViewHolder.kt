package com.badront.pokedex.evolution.widget.details

import android.view.ViewGroup
import by.kirich1409.viewbindingdelegate.viewBinding
import com.badront.pokedex.core.util.recycler.BaseViewHolder
import com.badront.pokedex.evolution.core.domain.model.EvolutionParam
import com.badront.pokedex.evolution.impl.R
import com.badront.pokedex.evolution.impl.databinding.LiEvolutionDetailNameImageBinding

class EvolutionLevelViewHolder(parent: ViewGroup) :
    BaseViewHolder<EvolutionParam.MinLevel>(parent, R.layout.li_evolution_detail_name_image) {
    private val binding: LiEvolutionDetailNameImageBinding by viewBinding()

    init {
        binding.image.setImageResource(R.drawable.ic_evolution_by_level)
    }

    override fun bind(item: EvolutionParam.MinLevel) {
        super.bind(item)
        binding.name.text = itemView.context.getString(R.string.evolution_level_template, item.value)
    }
}