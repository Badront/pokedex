package com.badront.pokedex.evolution.widget.details

import android.view.ViewGroup
import by.kirich1409.viewbindingdelegate.viewBinding
import com.badront.pokedex.core.util.recycler.BaseViewHolder
import com.badront.pokedex.evolution.core.domain.model.EvolutionParam
import com.badront.pokedex.evolution.impl.R
import com.badront.pokedex.evolution.impl.databinding.LiEvolutionDetailTextBinding

class EvolutionTimeOfDayViewHolder(parent: ViewGroup) :
    BaseViewHolder<EvolutionParam.TimeOfDay>(parent, R.layout.li_evolution_detail_text) {
    private val binding: LiEvolutionDetailTextBinding by viewBinding()
    private val dayStr: String = itemView.context.getString(R.string.evolution_day)
    private val nightStr: String = itemView.context.getString(R.string.evolution_night)
    override fun bind(item: EvolutionParam.TimeOfDay) {
        super.bind(item)
        binding.name.text = when (item) {
            EvolutionParam.TimeOfDay.Day -> dayStr
            EvolutionParam.TimeOfDay.Night -> nightStr
        }
    }
}