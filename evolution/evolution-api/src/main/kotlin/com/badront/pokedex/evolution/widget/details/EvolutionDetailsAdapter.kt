package com.badront.pokedex.evolution.widget.details

import android.view.ViewGroup
import androidx.collection.SparseArrayCompat
import androidx.recyclerview.widget.DiffUtil
import com.badront.pokedex.core.util.recycler.BaseAsyncAdapter
import com.badront.pokedex.core.util.recycler.BaseViewHolder
import com.badront.pokedex.evolution.core.domain.model.EvolutionParam
import com.badront.pokedex.item.core.domain.model.Item
import java.util.concurrent.atomic.AtomicInteger
import kotlin.reflect.KClass

internal class EvolutionDetailsAdapter(
    private val onItemClick: (Item) -> Unit
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
        val kClass = viewTypes[viewType]

        return when (kClass) {
            EvolutionParam.Gender.Male::class, EvolutionParam.Gender.Female::class -> {
                EvolutionGenderViewHolder(
                    parent
                )
            }
            EvolutionParam.HeldItemParam::class -> EvolutionHeldItemViewHolder(parent, onItemClick)
            EvolutionParam.ItemParam::class -> EvolutionItemViewHolder(parent, onItemClick)
            EvolutionParam.Location::class -> TODO()
            EvolutionParam.MinAffection::class -> TODO()
            EvolutionParam.MinBeauty::class -> TODO()
            EvolutionParam.MinHappiness::class -> EvolutionHappinessViewHolder(parent)
            EvolutionParam.MinLevel::class -> EvolutionLevelViewHolder(parent)
            EvolutionParam.MoveParam::class -> TODO()
            EvolutionParam.MoveTypeParam::class -> TODO()
            EvolutionParam.OverworldRain::class -> TODO()
            EvolutionParam.PartySpecies::class -> TODO()
            EvolutionParam.PartyType::class -> TODO()
            EvolutionParam.PhysicalStatsRelation.Attack::class,
            EvolutionParam.PhysicalStatsRelation.Defense::class,
            EvolutionParam.PhysicalStatsRelation.Equal::class -> TODO()
            EvolutionParam.TimeOfDay.Day::class,
            EvolutionParam.TimeOfDay.Night::class -> TODO()
            EvolutionParam.TradeSpecies::class -> TODO()
            EvolutionParam.TurnUpsideDown::class -> TODO()
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
            is EvolutionParam.MinHappiness -> (holder as EvolutionHappinessViewHolder).bind(item)
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