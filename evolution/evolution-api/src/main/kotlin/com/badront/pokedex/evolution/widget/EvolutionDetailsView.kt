package com.badront.pokedex.evolution.widget

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.badront.pokedex.evolution.core.domain.model.EvolutionDetails
import com.badront.pokedex.evolution.widget.details.EvolutionDetailsAdapter
import com.badront.pokedex.item.core.domain.model.Item

class EvolutionDetailsView @JvmOverloads constructor(
    context: Context,
    attr: AttributeSet? = null,
    defStyle: Int = 0
) : RecyclerView(context, attr, defStyle) {
    private val detailsAdapter by lazy {
        EvolutionDetailsAdapter(
            onItemClick = {
                onItemClickListener?.invoke(it)
            }
        )
    }
    var details: List<EvolutionDetails> = emptyList()
        set(value) {
            if (field != value) {
                field = value
                detailsAdapter.setItems(value.firstOrNull()?.params ?: emptyList())
            }
        }
    var onItemClickListener: ((Item) -> Unit)? = null

    init {
        layoutManager = LinearLayoutManager(context, VERTICAL, false)
        adapter = detailsAdapter
    }
}