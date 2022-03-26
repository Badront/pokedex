package com.badront.pokedex.pokemon.details.presentation.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.badront.pokedex.core.ext.kotlin.collections.areContentsTheSame
import com.badront.pokedex.core.util.recycler.BaseAsyncAdapter
import com.badront.pokedex.core.util.recycler.BaseViewHolder
import com.badront.pokedex.pokemon.details.presentation.model.PokemonDetailsUiModel
import com.badront.pokedex.pokemon.impl.R

class PokemonDetailsAdapter(
    private val onRetryClick: () -> Unit
) :
    BaseAsyncAdapter<PokemonDetailsUiModel, BaseViewHolder<out PokemonDetailsUiModel>>(Companion) {

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is PokemonDetailsUiModel.Description -> R.layout.li_pokemon_details_description
            is PokemonDetailsUiModel.Header -> R.layout.li_pokemon_details_header
            is PokemonDetailsUiModel.Measurements -> R.layout.li_pokemon_details_measurements
            is PokemonDetailsUiModel.Types -> R.layout.li_pokemon_details_types
            PokemonDetailsUiModel.Loading -> R.layout.li_pokemon_details_loading
            is PokemonDetailsUiModel.LoadingError -> R.layout.li_pokemon_details_loading_error
            is PokemonDetailsUiModel.Evolution -> R.layout.li_pokemon_details_evolution
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<out PokemonDetailsUiModel> {
        return when (viewType) {
            R.layout.li_pokemon_details_types -> PokemonTypesViewHolder(parent)
            R.layout.li_pokemon_details_header -> PokemonDetailsHeaderViewHolder(parent)
            R.layout.li_pokemon_details_description -> PokemonDetailsDescriptionViewHolder(parent)
            R.layout.li_pokemon_details_measurements -> PokemonDetailsMeasurementsViewHolder(parent)
            R.layout.li_pokemon_details_evolution -> PokemonDetailsEvolutionViewHolder(parent)
            R.layout.li_pokemon_details_loading -> PokemonDetailsLoadingViewHolder(parent)
            R.layout.li_pokemon_details_loading_error -> PokemonDetailsLoadingErrorViewHolder(parent, onRetryClick)
            else -> throw IllegalArgumentException("not ready for this viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<out PokemonDetailsUiModel>, position: Int) {
        when (val item = getItem(position)) {
            is PokemonDetailsUiModel.Description -> (holder as PokemonDetailsDescriptionViewHolder).bind(item)
            is PokemonDetailsUiModel.Header -> (holder as PokemonDetailsHeaderViewHolder).bind(item)
            is PokemonDetailsUiModel.Measurements -> (holder as PokemonDetailsMeasurementsViewHolder).bind(item)
            is PokemonDetailsUiModel.Types -> (holder as PokemonTypesViewHolder).bind(item)
            is PokemonDetailsUiModel.Evolution -> (holder as PokemonDetailsEvolutionViewHolder).bind(item)
            else -> {
                // ignore
            }
        }
    }

    private companion object : DiffUtil.ItemCallback<PokemonDetailsUiModel>() {
        override fun areItemsTheSame(oldItem: PokemonDetailsUiModel, newItem: PokemonDetailsUiModel): Boolean {
            return when {
                oldItem.javaClass != newItem.javaClass -> false
                oldItem is PokemonDetailsUiModel.Header && newItem is PokemonDetailsUiModel.Header -> {
                    oldItem.text == newItem.text
                }
                else -> true
            }
        }

        override fun areContentsTheSame(oldItem: PokemonDetailsUiModel, newItem: PokemonDetailsUiModel): Boolean {
            return when {
                oldItem is PokemonDetailsUiModel.Types && newItem is PokemonDetailsUiModel.Types -> {
                    oldItem.items.areContentsTheSame(newItem.items)
                }
                else -> oldItem == newItem
            }
        }
    }
}