package com.badront.pokedex.pokemon.core.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.badront.pokedex.pokemon.api.databinding.PokemonViewBinding
import com.badront.pokedex.pokemon.core.domain.model.Pokemon

class PokemonView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : LinearLayout(context, attrs, defStyle) {
    private val binding = PokemonViewBinding.inflate(LayoutInflater.from(context), this)
    var pokemon: Pokemon? = null
        set(value) {
            field = value
            binding.image.loadPokemon(pokemon?.image ?: "")
            binding.name.text = pokemon?.name
        }

    var onPokemonClickListener: ((Pokemon) -> Unit)? = null

    init {
        orientation = VERTICAL
        setBackgroundResource(com.badront.pokedex.design.R.drawable.ripple_rounded_8)
        setOnClickListener {
            pokemon?.let { onPokemonClickListener?.invoke(it) }
        }
    }
}