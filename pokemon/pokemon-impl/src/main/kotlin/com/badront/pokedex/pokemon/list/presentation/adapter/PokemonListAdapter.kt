package com.badront.pokedex.pokemon.list.presentation.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.badront.pokedex.core.ext.androidx.palette.graphics.ColorPalette
import com.badront.pokedex.core.util.recycler.BaseAsyncAdapter
import com.badront.pokedex.pokemon.impl.R
import com.badront.pokedex.pokemon.list.presentation.model.PokemonListUiModel

internal class PokemonListAdapter(
    private val onPokemonClick: (position: Int, pokemon: PokemonListUiModel.Pokemon) -> Unit,
    private val onPokemonPaletteLoaded: (pokemon: PokemonListUiModel.Pokemon, palette: ColorPalette) -> Unit,
    private val onNextPageLoadingRetryClick: () -> Unit
) : BaseAsyncAdapter<PokemonListUiModel, RecyclerView.ViewHolder>(Companion) {
    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            PokemonListUiModel.NextPageLoading -> R.layout.li_pokemon_page_loading
            PokemonListUiModel.NextPageLoadingError -> R.layout.li_pokemon_page_loading_error
            is PokemonListUiModel.Pokemon -> R.layout.li_pokemon_item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.li_pokemon_item -> PokemonItemViewHolder(parent, onPokemonClick, onPokemonPaletteLoaded)
            R.layout.li_pokemon_page_loading_error -> PokemonNextPageLoadingErrorViewHolder(
                parent,
                onNextPageLoadingRetryClick
            )
            else -> PokemonNextPageLoadingViewHolder(parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        if (item is PokemonListUiModel.Pokemon) {
            (holder as PokemonItemViewHolder).bind(item)
        }
    }

    private companion object : DiffUtil.ItemCallback<PokemonListUiModel>() {
        override fun areItemsTheSame(oldItem: PokemonListUiModel, newItem: PokemonListUiModel): Boolean {
            return when {
                oldItem.javaClass != newItem.javaClass -> false
                oldItem is PokemonListUiModel.Pokemon && newItem is PokemonListUiModel.Pokemon -> {
                    oldItem.id == newItem.id
                }
                else -> true
            }
        }

        override fun areContentsTheSame(oldItem: PokemonListUiModel, newItem: PokemonListUiModel): Boolean {
            return oldItem == newItem
        }
    }
}