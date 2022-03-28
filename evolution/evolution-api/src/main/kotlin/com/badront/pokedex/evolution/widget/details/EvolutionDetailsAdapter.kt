package com.badront.pokedex.evolution.widget.details

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.badront.pokedex.core.util.recycler.BaseAsyncAdapter
import com.badront.pokedex.core.util.recycler.BaseViewHolder
import com.badront.pokedex.evolution.core.domain.model.EvolutionParam
import com.badront.pokedex.evolution.impl.R
import com.badront.pokedex.item.core.domain.model.Item

internal class EvolutionDetailsAdapter(
    private val onItemClick: (Item) -> Unit
) : BaseAsyncAdapter<EvolutionParam, BaseViewHolder<out EvolutionParam>>(Companion) {

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is EvolutionParam.Gender -> R.layout.li_evolution_detail_gender
            is EvolutionParam.HeldItemParam -> R.layout.li_evolution_detail_held_item
            is EvolutionParam.ItemParam -> R.layout.li_evolution_detail_item
            is EvolutionParam.Location -> TODO()
            is EvolutionParam.MinAffection -> TODO()
            is EvolutionParam.MinBeauty -> TODO()
            is EvolutionParam.MinHappiness -> TODO()
            is EvolutionParam.MinLevel -> R.layout.li_evolution_detail_lvl
            is EvolutionParam.MoveParam -> TODO()
            is EvolutionParam.MoveTypeParam -> TODO()
            EvolutionParam.OverworldRain -> TODO()
            is EvolutionParam.PartySpecies -> TODO()
            is EvolutionParam.PartyType -> TODO()
            EvolutionParam.PhysicalStatsRelation.Attack -> TODO()
            EvolutionParam.PhysicalStatsRelation.Defense -> TODO()
            EvolutionParam.PhysicalStatsRelation.Equal -> TODO()
            EvolutionParam.TimeOfDay.Day -> TODO()
            EvolutionParam.TimeOfDay.Night -> TODO()
            is EvolutionParam.TradeSpecies -> TODO()
            EvolutionParam.TurnUpsideDown -> TODO()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<out EvolutionParam> {
        return when (viewType) {
            R.layout.li_evolution_detail_gender -> EvolutionGenderViewHolder(parent)
            R.layout.li_evolution_detail_held_item -> EvolutionHeldItemViewHolder(parent, onItemClick)
            R.layout.li_evolution_detail_item -> EvolutionItemViewHolder(parent, onItemClick)
            R.layout.li_evolution_detail_lvl -> EvolutionLevelViewHolder(parent)
            else -> TODO()
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<out EvolutionParam>, position: Int) {
        return when (val item = getItem(position)) {
            is EvolutionParam.Gender -> (holder as EvolutionGenderViewHolder).bind(item)
            is EvolutionParam.HeldItemParam -> (holder as EvolutionHeldItemViewHolder).bind(item)
            is EvolutionParam.ItemParam -> (holder as EvolutionItemViewHolder).bind(item)
            is EvolutionParam.Location -> TODO()
            is EvolutionParam.MinAffection -> TODO()
            is EvolutionParam.MinBeauty -> TODO()
            is EvolutionParam.MinHappiness -> TODO()
            is EvolutionParam.MinLevel -> (holder as EvolutionLevelViewHolder).bind(item)
            is EvolutionParam.MoveParam -> TODO()
            is EvolutionParam.MoveTypeParam -> TODO()
            EvolutionParam.OverworldRain -> TODO()
            is EvolutionParam.PartySpecies -> TODO()
            is EvolutionParam.PartyType -> TODO()
            EvolutionParam.PhysicalStatsRelation.Attack -> TODO()
            EvolutionParam.PhysicalStatsRelation.Defense -> TODO()
            EvolutionParam.PhysicalStatsRelation.Equal -> TODO()
            EvolutionParam.TimeOfDay.Day -> TODO()
            EvolutionParam.TimeOfDay.Night -> TODO()
            is EvolutionParam.TradeSpecies -> TODO()
            EvolutionParam.TurnUpsideDown -> TODO()
        }
    }

    private companion object : DiffUtil.ItemCallback<EvolutionParam>() {
        override fun areItemsTheSame(oldItem: EvolutionParam, newItem: EvolutionParam): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: EvolutionParam, newItem: EvolutionParam): Boolean {
            return oldItem == newItem
        }
    }
}