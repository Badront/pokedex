package com.badront.pokedex.pokemon.details.presentation.adapter

import android.content.res.ColorStateList
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import by.kirich1409.viewbindingdelegate.viewBinding
import com.badront.pokedex.core.ext.model.setText
import com.badront.pokedex.core.util.recycler.BaseAsyncAdapter
import com.badront.pokedex.core.util.recycler.BaseViewHolder
import com.badront.pokedex.pokemon.details.presentation.model.PokemonDetailsUiModel
import com.badront.pokedex.pokemon.impl.R
import com.badront.pokedex.pokemon.impl.databinding.LiPokemonDetailsTypesItemBinding

class PokemonDetailsTypesAdapter :
    BaseAsyncAdapter<PokemonDetailsUiModel.Types.Type, PokemonDetailsTypesAdapter.TypeViewHolder>(Companion) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TypeViewHolder {
        return TypeViewHolder(parent)
    }

    override fun onBindViewHolder(holder: TypeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class TypeViewHolder(parent: ViewGroup) :
        BaseViewHolder<PokemonDetailsUiModel.Types.Type>(parent, R.layout.li_pokemon_details_types_item) {
        private val viewBinding by viewBinding<LiPokemonDetailsTypesItemBinding>()
        override fun bind(item: PokemonDetailsUiModel.Types.Type) {
            super.bind(item)
            with(viewBinding.typeName) {
                setText(item.name)
                backgroundTintList = ColorStateList.valueOf(item.color)
            }
        }
    }

    private companion object : DiffUtil.ItemCallback<PokemonDetailsUiModel.Types.Type>() {
        override fun areItemsTheSame(
            oldItem: PokemonDetailsUiModel.Types.Type,
            newItem: PokemonDetailsUiModel.Types.Type
        ): Boolean {
            return oldItem.type == newItem.type
        }

        override fun areContentsTheSame(
            oldItem: PokemonDetailsUiModel.Types.Type,
            newItem: PokemonDetailsUiModel.Types.Type
        ): Boolean {
            return oldItem.color == newItem.color && oldItem.name == newItem.name
        }
    }
}