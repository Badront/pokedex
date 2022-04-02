package com.badront.pokedex.evolution.widget.details

import android.view.ViewGroup
import by.kirich1409.viewbindingdelegate.viewBinding
import com.badront.pokedex.core.util.recycler.BaseViewHolder
import com.badront.pokedex.evolution.core.domain.model.EvolutionParam
import com.badront.pokedex.evolution.impl.R
import com.badront.pokedex.evolution.impl.databinding.LiEvolutionDetailTextBinding

class EvolutionPhysicalStatsRelationHolder(parent: ViewGroup) :
    BaseViewHolder<EvolutionParam.PhysicalStatsRelation>(parent, R.layout.li_evolution_detail_text) {
    private val binding: LiEvolutionDetailTextBinding by viewBinding()
    override fun bind(item: EvolutionParam.PhysicalStatsRelation) {
        super.bind(item)
        binding.name.setText(
            when (item) {
                EvolutionParam.PhysicalStatsRelation.Attack -> R.string.evolution_physical_stats_relation_attack
                EvolutionParam.PhysicalStatsRelation.Defense -> R.string.evolution_physical_stats_relation_defence
                EvolutionParam.PhysicalStatsRelation.Equal -> R.string.evolution_physical_stats_relation_equal
            }
        )
    }
}