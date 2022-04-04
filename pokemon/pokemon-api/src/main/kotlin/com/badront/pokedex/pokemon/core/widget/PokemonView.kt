package com.badront.pokedex.pokemon.core.widget

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.annotation.DimenRes
import androidx.core.view.updateLayoutParams
import com.badront.pokedex.core.ext.android.content.getDimensionKtx
import com.badront.pokedex.core.ext.android.content.getDimensionPixelSizeKtx
import com.badront.pokedex.pokemon.api.R
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
        val array = context.obtainStyledAttributes(attrs, R.styleable.PokemonView, defStyle, 0)
        val sizeValue = array.getInt(R.styleable.PokemonView_pv_size, 0)
        applySizeValue(sizeValue)
        array.recycle()
        orientation = VERTICAL
        gravity = Gravity.CENTER_HORIZONTAL
        setBackgroundResource(com.badront.pokedex.design.R.drawable.ripple_rounded_8)
        setOnClickListener {
            pokemon?.let { onPokemonClickListener?.invoke(it) }
        }
    }

    private fun applySizeValue(sizeValue: Int) {
        Size.fromValue(sizeValue)?.let { size ->
            val imageSize = context.getDimensionPixelSizeKtx(size.imageSize)
            binding.image.updateLayoutParams<ViewGroup.LayoutParams> {
                width = imageSize
                height = imageSize
            }
            binding.name.setTextSize(
                TypedValue.COMPLEX_UNIT_PX,
                context.getDimensionKtx(size.textSize)
            )
        }
    }

    enum class Size(
        @DimenRes val imageSize: Int,
        @DimenRes val textSize: Int
    ) {
        NORMAL(R.dimen.pokemon_size_default, com.badront.pokedex.design.R.dimen.text_10),
        SMALL(R.dimen.pokemon_size_small, com.badront.pokedex.design.R.dimen.text_8);

        fun toValue(): Int = ordinal

        companion object {
            fun fromValue(value: Int): Size? {
                return if (value < values().size) {
                    values()[value]
                } else {
                    null
                }
            }
        }
    }
}