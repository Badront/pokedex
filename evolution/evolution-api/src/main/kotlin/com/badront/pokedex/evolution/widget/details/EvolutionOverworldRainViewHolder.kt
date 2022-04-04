package com.badront.pokedex.evolution.widget.details

import android.view.ViewGroup
import by.kirich1409.viewbindingdelegate.viewBinding
import com.badront.pokedex.core.util.recycler.BaseViewHolder
import com.badront.pokedex.evolution.core.domain.model.EvolutionParam
import com.badront.pokedex.evolution.impl.R
import com.badront.pokedex.evolution.impl.databinding.LiEvolutionDetailTextBinding

class EvolutionOverworldRainViewHolder(parent: ViewGroup) :
    BaseViewHolder<EvolutionParam.OverworldRain>(parent, R.layout.li_evolution_detail_text) {
    private val binding: LiEvolutionDetailTextBinding by viewBinding()

    init {
        binding.name.setText(R.string.evolution_overworld_rain)
    }
}