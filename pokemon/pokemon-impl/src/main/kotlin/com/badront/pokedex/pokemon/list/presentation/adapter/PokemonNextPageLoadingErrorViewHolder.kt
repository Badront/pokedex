package com.badront.pokedex.pokemon.list.presentation.adapter

import android.view.ViewGroup
import com.badront.pokedex.core.util.recycler.BaseViewHolder
import com.badront.pokedex.design.widget.ErrorRetryView
import com.badront.pokedex.pokemon.impl.R
import com.badront.pokedex.pokemon.list.presentation.model.PokemonListUiModel

internal class PokemonNextPageLoadingErrorViewHolder(
    parent: ViewGroup,
    onRetryClick: () -> Unit
) : BaseViewHolder<PokemonListUiModel.NextPageLoadingError>(parent, R.layout.li_pokemon_page_loading_error) {

    init {
        (itemView as ErrorRetryView).onRetryClickListener = { onRetryClick() }
    }
}