package com.badront.pokedex.pokemon.core.widget

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.AttributeSet
import android.util.TypedValue
import androidx.annotation.DimenRes
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.view.updatePadding
import com.badront.pokedex.core.ext.android.content.getColorKtx
import com.badront.pokedex.core.ext.android.content.getDimensionKtx
import com.badront.pokedex.core.ext.android.content.getDimensionPixelOffsetKtx
import com.badront.pokedex.pokemon.api.R
import com.badront.pokedex.pokemon.core.domain.model.PokemonType
import com.google.android.material.textview.MaterialTextView
import java.util.Locale

class PokemonTypeView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : MaterialTextView(
    ContextThemeWrapper(context, com.badront.pokedex.design.R.style.Text_Bold_10_White),
    attrs,
    defStyle
) {
    var type: PokemonType.Type? = null
        set(value) {
            field = value
            text = value?.let { getTypeName(it) }
            backgroundTintList =
                value?.let { ColorStateList.valueOf(getTypeColor(it)) } ?: ColorStateList.valueOf(Color.WHITE)
        }

    init {
        setBackgroundResource(R.drawable.li_pokemon_details_types_item_bg)
        val array = context.obtainStyledAttributes(attrs, R.styleable.PokemonTypeView, defStyle, 0)
        val sizeValue = array.getInt(R.styleable.PokemonTypeView_ptv_size, 0)
        applySizeValue(sizeValue)
        array.recycle()
    }

    private fun applySizeValue(sizeValue: Int) {
        val size = Size.fromValue(sizeValue) ?: Size.NORMAL
        val horizontalPadding = context.getDimensionPixelOffsetKtx(size.paddingVertical)
        val verticalPadding = context.getDimensionPixelOffsetKtx(com.badront.pokedex.design.R.dimen.offset_2)
        updatePadding(
            left = horizontalPadding,
            top = verticalPadding,
            right = horizontalPadding,
            bottom = verticalPadding
        )
        setTextSize(
            TypedValue.COMPLEX_UNIT_PX,
            context.getDimensionKtx(size.textSize)
        )
    }

    private fun getTypeName(type: PokemonType.Type): String {
        return type.name.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
        }
    }

    private fun getTypeColor(type: PokemonType.Type): Int {
        return context.getColorKtx(type.getColorResId())
    }

    enum class Size(
        @DimenRes val paddingVertical: Int,
        @DimenRes val textSize: Int
    ) {
        NORMAL(com.badront.pokedex.design.R.dimen.offset_8, com.badront.pokedex.design.R.dimen.text_10),
        SMALL(com.badront.pokedex.design.R.dimen.offset_2, com.badront.pokedex.design.R.dimen.text_6);

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