package com.badront.pokedex.evolution.widget

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.badront.pokedex.core.ext.kotlin.lazyUnsynchronized
import com.badront.pokedex.evolution.core.domain.model.EvolutionDetails
import com.badront.pokedex.evolution.widget.details.EvolutionParamsAdapter
import com.badront.pokedex.item.core.domain.model.Item
import com.badront.pokedex.pokemon.core.domain.model.Pokemon

class EvolutionParamsView @JvmOverloads constructor(
    context: Context,
    attr: AttributeSet? = null,
    defStyle: Int = 0
) : RecyclerView(context, attr, defStyle) {
    private val paramsAdapter by lazyUnsynchronized {
        EvolutionParamsAdapter(
            onItemClick = {
                onItemClickListener?.invoke(it)
            },
            onPokemonClick = {
                onPokemonClickListener?.invoke(it)
            }
        )
    }
    var details: List<EvolutionDetails> = emptyList()
        set(value) {
            if (field != value) {
                field = value
                paramsAdapter.setItems(value.firstOrNull()?.params ?: emptyList())
            }
        }
    var onItemClickListener: ((Item) -> Unit)? = null
    var onPokemonClickListener: ((Pokemon) -> Unit)? = null

    init {
        layoutManager = LinearLayoutManager(context, VERTICAL, true).apply {
            stackFromEnd = false
        }
        adapter = paramsAdapter
    }
}