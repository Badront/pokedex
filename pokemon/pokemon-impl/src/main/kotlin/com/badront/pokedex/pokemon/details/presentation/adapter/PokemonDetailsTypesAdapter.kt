package com.badront.pokedex.pokemon.details.presentation.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import by.kirich1409.viewbindingdelegate.viewBinding
import com.badront.pokedex.core.util.recycler.BaseAsyncAdapter
import com.badront.pokedex.core.util.recycler.BaseViewHolder
import com.badront.pokedex.pokemon.core.widget.PokemonTypeUiModel
import com.badront.pokedex.pokemon.impl.R
import com.badront.pokedex.pokemon.impl.databinding.LiPokemonDetailsTypesItemBinding

class PokemonDetailsTypesAdapter :
    BaseAsyncAdapter<PokemonTypeUiModel, PokemonDetailsTypesAdapter.TypeViewHolder>(Companion) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TypeViewHolder {
        return TypeViewHolder(parent)
    }

    override fun onBindViewHolder(holder: TypeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class TypeViewHolder(parent: ViewGroup) :
        BaseViewHolder<PokemonTypeUiModel>(parent, R.layout.li_pokemon_details_types_item) {
        private val viewBinding by viewBinding<LiPokemonDetailsTypesItemBinding>()
        override fun bind(item: PokemonTypeUiModel) {
            super.bind(item)
            viewBinding.type.type = item.type
        }
    }

    private companion object : DiffUtil.ItemCallback<PokemonTypeUiModel>() {
        override fun areItemsTheSame(
            oldItem: PokemonTypeUiModel,
            newItem: PokemonTypeUiModel
        ): Boolean {
            return oldItem.type == newItem.type
        }

        override fun areContentsTheSame(
            oldItem: PokemonTypeUiModel,
            newItem: PokemonTypeUiModel
        ): Boolean {
            return oldItem.type == newItem.type
        }
    }
}