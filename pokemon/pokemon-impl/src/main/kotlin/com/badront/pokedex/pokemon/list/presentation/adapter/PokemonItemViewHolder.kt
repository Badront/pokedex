package com.badront.pokedex.pokemon.list.presentation.adapter

import android.view.ViewGroup
import android.widget.ImageView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.badront.pokedex.core.util.recycler.BaseViewHolder
import com.badront.pokedex.pokemon.impl.R
import com.badront.pokedex.pokemon.impl.databinding.LiPokemonItemBinding
import com.badront.pokedex.pokemon.list.presentation.model.PokemonListUiModel

internal class PokemonItemViewHolder(
    parent: ViewGroup,
    onClick: (position: Int, pokemon: PokemonListUiModel.Pokemon) -> Unit
) : BaseViewHolder<PokemonListUiModel.Pokemon>(parent, R.layout.li_pokemon_item) {
    private val viewBinding: LiPokemonItemBinding by viewBinding()
    val pokemonImage: ImageView
        get() = viewBinding.listPokemon.imageView

    init {
        viewBinding.root.setOnClickListener {
            item?.let {
                onClick(bindingAdapterPosition, it)
            }
        }
    }

    override fun bind(item: PokemonListUiModel.Pokemon) {
        super.bind(item)
        with(viewBinding.listPokemon) {
            setPokemon(item)
            imageView.tag = item.image
            imageView.contentDescription = item.name
        }
    }
}