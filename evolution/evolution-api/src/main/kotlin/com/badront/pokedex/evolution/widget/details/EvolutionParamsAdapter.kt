package com.badront.pokedex.evolution.widget.details

import android.view.ViewGroup
import androidx.collection.SparseArrayCompat
import androidx.recyclerview.widget.DiffUtil
import com.badront.pokedex.core.util.recycler.BaseAsyncAdapter
import com.badront.pokedex.core.util.recycler.BaseViewHolder
import com.badront.pokedex.evolution.core.domain.model.EvolutionParam
import com.badront.pokedex.item.core.domain.model.Item
import com.badront.pokedex.pokemon.core.domain.model.Pokemon
import java.util.concurrent.atomic.AtomicInteger
import kotlin.reflect.KClass

internal class EvolutionParamsAdapter(
    private val onItemClick: (Item) -> Unit,
    private val onPokemonClick: (Pokemon) -> Unit
) : BaseAsyncAdapter<EvolutionParam, BaseViewHolder<out EvolutionParam>>(itemCallback) {
    private val currentMaxViewType = AtomicInteger()
    private val viewTypes = SparseArrayCompat<KClass<out EvolutionParam>>()

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        val count = viewTypes.size()
        for (i in 0 until count) {
            if (viewTypes.valueAt(i) == item::class) {
                return viewTypes.keyAt(i)
            }
        }
        return currentMaxViewType.incrementAndGet().also {
            viewTypes.put(it, item::class)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<out EvolutionParam> {
        return when (viewTypes[viewType]) {
            EvolutionParam.Gender.Male::class, EvolutionParam.Gender.Female::class -> {
                EvolutionGenderViewHolder(
                    parent
                )
            }
            EvolutionParam.HeldItemParam::class -> EvolutionHeldItemViewHolder(parent, onItemClick)
            EvolutionParam.ItemParam::class -> EvolutionItemViewHolder(parent, onItemClick)
            EvolutionParam.Location::class -> EvolutionLocationViewHolder(parent)
            EvolutionParam.MinLevel::class -> EvolutionLevelViewHolder(parent)
            EvolutionParam.MinAffection::class -> EvolutionMinAffectionViewHolder(parent)
            EvolutionParam.MinBeauty::class -> EvolutionMinBeautyViewHolder(parent)
            EvolutionParam.MinHappiness::class -> EvolutionMinHappinessViewHolder(parent)
            EvolutionParam.MoveParam::class -> EvolutionKnownMoveViewHolder(parent)
            EvolutionParam.MoveTypeParam::class -> EvolutionKnownMoveTypeViewHolder(parent)
            EvolutionParam.OverworldRain::class -> EvolutionOverworldRainViewHolder(parent)
            EvolutionParam.PartySpecies::class -> EvolutionPartySpeciesViewHolder(parent, onPokemonClick)
            EvolutionParam.PartyType::class -> EvolutionPartyTypeViewHolder(parent)
            EvolutionParam.PhysicalStatsRelation.Attack::class,
            EvolutionParam.PhysicalStatsRelation.Defense::class,
            EvolutionParam.PhysicalStatsRelation.Equal::class -> EvolutionPhysicalStatsRelationHolder(parent)
            EvolutionParam.TimeOfDay.Day::class,
            EvolutionParam.TimeOfDay.Night::class -> EvolutionTimeOfDayViewHolder(parent)
            EvolutionParam.TradeSpecies::class -> EvolutionTradeSpeciesViewHolder(parent, onPokemonClick)
            EvolutionParam.TurnUpsideDown::class -> EvolutionTurnUpsideDownViewHolder(parent)
            else -> throw IllegalArgumentException("Item viewType unknown")
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<out EvolutionParam>, position: Int) {
        return when (val item = getItem(position)) {
            is EvolutionParam.Gender -> (holder as EvolutionGenderViewHolder).bind(item)
            is EvolutionParam.HeldItemParam -> (holder as EvolutionHeldItemViewHolder).bind(item)
            is EvolutionParam.ItemParam -> (holder as EvolutionItemViewHolder).bind(item)
            is EvolutionParam.Location -> (holder as EvolutionLocationViewHolder).bind(item)
            is EvolutionParam.MinAffection -> (holder as EvolutionMinAffectionViewHolder).bind(item)
            is EvolutionParam.MinBeauty -> (holder as EvolutionMinBeautyViewHolder).bind(item)
            is EvolutionParam.MinHappiness -> (holder as EvolutionMinHappinessViewHolder).bind(item)
            is EvolutionParam.MinLevel -> (holder as EvolutionLevelViewHolder).bind(item)
            is EvolutionParam.MoveParam -> (holder as EvolutionKnownMoveViewHolder).bind(item)
            is EvolutionParam.MoveTypeParam -> (holder as EvolutionKnownMoveTypeViewHolder).bind(item)
            is EvolutionParam.OverworldRain -> (holder as EvolutionOverworldRainViewHolder).bind(item)
            is EvolutionParam.PartySpecies -> (holder as EvolutionPartySpeciesViewHolder).bind(item)
            is EvolutionParam.PartyType -> (holder as EvolutionPartyTypeViewHolder).bind(item)
            is EvolutionParam.PhysicalStatsRelation.Attack -> {
                (holder as EvolutionPhysicalStatsRelationHolder).bind(item)
            }
            is EvolutionParam.PhysicalStatsRelation.Defense -> {
                (holder as EvolutionPhysicalStatsRelationHolder).bind(item)
            }
            is EvolutionParam.PhysicalStatsRelation.Equal -> {
                (holder as EvolutionPhysicalStatsRelationHolder).bind(item)
            }
            is EvolutionParam.TimeOfDay.Day -> {
                (holder as EvolutionTimeOfDayViewHolder).bind(item)
            }
            is EvolutionParam.TimeOfDay.Night -> {
                (holder as EvolutionTimeOfDayViewHolder).bind(item)
            }
            is EvolutionParam.TradeSpecies -> (holder as EvolutionTradeSpeciesViewHolder).bind(item)
            is EvolutionParam.TurnUpsideDown -> (holder as EvolutionTurnUpsideDownViewHolder).bind(item)
        }
    }

    private companion object {
        val itemCallback = object : DiffUtil.ItemCallback<EvolutionParam>() {
            override fun areItemsTheSame(oldItem: EvolutionParam, newItem: EvolutionParam): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: EvolutionParam, newItem: EvolutionParam): Boolean {
                return oldItem == newItem
            }
        }
    }
}