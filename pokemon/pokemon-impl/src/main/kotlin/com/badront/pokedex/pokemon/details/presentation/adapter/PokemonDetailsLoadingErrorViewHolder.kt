package com.badront.pokedex.pokemon.details.presentation.adapter

import android.view.ViewGroup
import by.kirich1409.viewbindingdelegate.viewBinding
import com.badront.pokedex.core.util.recycler.BaseViewHolder
import com.badront.pokedex.pokemon.details.presentation.model.PokemonDetailsUiModel
import com.badront.pokedex.pokemon.impl.R
import com.badront.pokedex.pokemon.impl.databinding.LiPokemonDetailsLoadingErrorBinding

class PokemonDetailsLoadingErrorViewHolder(
    parent: ViewGroup,
    onRetryClick: () -> Unit
) : BaseViewHolder<PokemonDetailsUiModel.LoadingError>(parent, R.layout.li_pokemon_details_loading_error) {
    private val binding: LiPokemonDetailsLoadingErrorBinding by viewBinding()

    init {
        binding.root.onRetryClickListener = onRetryClick
    }
}