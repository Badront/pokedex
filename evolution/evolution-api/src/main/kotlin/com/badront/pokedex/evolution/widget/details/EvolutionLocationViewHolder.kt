package com.badront.pokedex.evolution.widget.details

import android.view.ViewGroup
import by.kirich1409.viewbindingdelegate.viewBinding
import com.badront.pokedex.core.ext.model.setText
import com.badront.pokedex.core.model.StringDesc
import com.badront.pokedex.core.util.recycler.BaseViewHolder
import com.badront.pokedex.evolution.core.domain.model.EvolutionParam
import com.badront.pokedex.evolution.impl.R
import com.badront.pokedex.evolution.impl.databinding.LiEvolutionDetailTextBinding

class EvolutionLocationViewHolder(parent: ViewGroup) :
    BaseViewHolder<EvolutionParam.Location>(parent, R.layout.li_evolution_detail_text) {
    private val binding: LiEvolutionDetailTextBinding by viewBinding()

    override fun bind(item: EvolutionParam.Location) {
        super.bind(item)
        binding.name.setText(StringDesc.StringR(R.string.evolution_location_template, arrayOf(item.name)))
    }
}