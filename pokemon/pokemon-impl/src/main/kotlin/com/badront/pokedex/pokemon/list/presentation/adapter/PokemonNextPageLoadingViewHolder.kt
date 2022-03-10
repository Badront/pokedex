package com.badront.pokedex.pokemon.list.presentation.adapter

import android.view.ViewGroup
import com.badront.pokedex.core.util.recycler.BaseViewHolder
import com.badront.pokedex.pokemon.impl.R
import com.badront.pokedex.pokemon.list.presentation.model.PokemonListUiModel

internal class PokemonNextPageLoadingViewHolder(
    parent: ViewGroup
) : BaseViewHolder<PokemonListUiModel.NextPageLoading>(parent, R.layout.li_pokemon_page_loading)