package com.badront.pokedex.pokemon.list.presentation.adapter

import android.view.ViewGroup
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import com.badront.pokedex.core.util.recycler.BaseViewHolder
import com.badront.pokedex.pokemon.impl.R
import com.badront.pokedex.pokemon.impl.databinding.LiPokemonItemBinding
import com.badront.pokedex.pokemon.list.presentation.model.PokemonListUiModel

internal class PokemonItemViewHolder(
    parent: ViewGroup,
    onClick: (PokemonListUiModel.Pokemon) -> Unit
) : BaseViewHolder<PokemonListUiModel.Pokemon>(parent, R.layout.li_pokemon_item) {
    private val viewBinding: LiPokemonItemBinding by viewBinding()

    init {
        viewBinding.root.setOnClickListener {
            item?.let(onClick)
        }
    }

    override fun bind(item: PokemonListUiModel.Pokemon) {
        super.bind(item)
        viewBinding.pokemonName.text = item.name
        viewBinding.pokemonImage.load(item.image) {
            placeholder(R.drawable.egg)
            crossfade(true)
        }
        viewBinding.pokemonImage.contentDescription = item.name
    }
}