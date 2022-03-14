package com.badront.pokedex.pokemon.details.presentation.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.badront.pokedex.core.ext.android.content.getDimensionPixelOffsetKtx
import com.badront.pokedex.core.util.recycler.BaseViewHolder
import com.badront.pokedex.core.util.recycler.LinearSpacingItemDecoration
import com.badront.pokedex.pokemon.details.presentation.model.PokemonDetailsUiModel
import com.badront.pokedex.pokemon.impl.R
import com.badront.pokedex.pokemon.impl.databinding.LiPokemonDetailsTypesBinding
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent

class PokemonTypesViewHolder(
    parent: ViewGroup
) : BaseViewHolder<PokemonDetailsUiModel.Types>(parent, R.layout.li_pokemon_details_types) {
    private val viewBinding by viewBinding<LiPokemonDetailsTypesBinding>()
    private val typeAdapter = PokemonDetailsTypesAdapter()
    private val spacingItemDecoration = LinearSpacingItemDecoration(
        betweenItems = itemView.context.getDimensionPixelOffsetKtx(com.badront.pokedex.design.R.dimen.offset_16),
        paddingLeft = itemView.context.getDimensionPixelOffsetKtx(com.badront.pokedex.design.R.dimen.offset_16),
        paddingRight = itemView.context.getDimensionPixelOffsetKtx(com.badront.pokedex.design.R.dimen.offset_16),
        orientation = RecyclerView.HORIZONTAL
    )

    init {
        with(viewBinding.pokemonTypes) {
            adapter = typeAdapter
            addItemDecoration(spacingItemDecoration)
            with(layoutManager as FlexboxLayoutManager) {
                justifyContent = JustifyContent.CENTER
                alignItems = AlignItems.CENTER
                flexDirection = FlexDirection.ROW
                flexWrap = FlexWrap.WRAP
            }
        }
    }

    override fun bind(item: PokemonDetailsUiModel.Types) {
        super.bind(item)
        typeAdapter.setItems(item.items)
    }
}