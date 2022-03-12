package com.badront.pokedex.pokemon.list.presentation

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import coil.load
import coil.request.Disposable
import com.badront.pokedex.core.ext.android.content.getColorKtx
import com.badront.pokedex.core.ext.android.content.getDimensionPixelSizeKtx
import com.badront.pokedex.core.ext.androidx.palette.graphics.ColorPalette
import com.badront.pokedex.core.ext.androidx.palette.graphics.getPalette
import com.badront.pokedex.pokemon.impl.R
import com.badront.pokedex.pokemon.impl.databinding.ListPokemonViewBinding
import com.badront.pokedex.pokemon.list.presentation.model.PokemonListUiModel

internal class ListPokemonView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attributeSet, defStyleAttr) {
    private val viewBinding = ListPokemonViewBinding.inflate(LayoutInflater.from(context), this)
    private val frameWidth = context.getDimensionPixelSizeKtx(R.dimen.list_pokemon_frame_width).toFloat()
    private val frameCornerRadius = context.getDimensionPixelSizeKtx(R.dimen.list_pokemon_frame_corner_radius).toFloat()
    private val bgPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = frameWidth
    }
    private val nameBgPath = Path()
    private val bgRect = RectF()
    private var defaultColorPalette: ColorPalette
    private var imageLoadingDisposable: Disposable? = null
    private var currentPokemon: PokemonListUiModel.Pokemon? = null
    val imageView: ImageView = viewBinding.pokemonImage
    var onPokemonPaletteLoadedListener: ((PokemonListUiModel.Pokemon, ColorPalette?) -> Unit)? = null

    init {
        val array = context.obtainStyledAttributes(attributeSet, R.styleable.ListPokemonView, defStyleAttr, 0)
        val frameColor = array.getColor(
            R.styleable.ListPokemonView_lpv_frameColor,
            context.getColorKtx(com.badront.pokedex.design.R.color.wireframe)
        )
        val onFrameColor = if (array.hasValue(R.styleable.ListPokemonView_lpv_onFrameTextColor)) {
            array.getColorStateList(R.styleable.ListPokemonView_lpv_onFrameTextColor)
                ?: context.getColorStateList(com.badront.pokedex.design.R.color.white)
        } else {
            context.getColorStateList(com.badront.pokedex.design.R.color.white)
        }
        defaultColorPalette = ColorPalette(
            primaryColor = frameColor,
            onPrimaryColor = onFrameColor.defaultColor
        )
        array.recycle()
        setWillNotDraw(false)
    }

    fun setPokemon(pokemon: PokemonListUiModel.Pokemon) {
        currentPokemon = pokemon
        imageLoadingDisposable?.dispose()
        with(viewBinding) {
            pokemonName.text = pokemon.name
            pokemonNumber.text = pokemon.number
            imageLoadingDisposable = pokemonImage.load(pokemon.image) {
                placeholder(R.drawable.egg)
                error(R.drawable.empty)
                crossfade(true)
                if (pokemon.palette == null) {
                    setColorPalette(pokemon, defaultColorPalette)
                    getPalette { colorPalette ->
                        colorPalette?.let { setColorPalette(pokemon, it) }
                        onPokemonPaletteLoadedListener?.invoke(pokemon, colorPalette)
                    }
                } else {
                    setColorPalette(pokemon, pokemon.palette)
                }
            }
        }
    }

    @Synchronized
    private fun setColorPalette(pokemon: PokemonListUiModel.Pokemon, palette: ColorPalette) {
        if (currentPokemon == pokemon) {
            viewBinding.pokemonName.setTextColor(palette.onPrimaryColor)
            bgPaint.color = palette.primaryColor
            invalidate()
        }
    }

    override fun onSaveInstanceState(): Parcelable {
        val superState = super.onSaveInstanceState()
        val savedState = SavedState(superState ?: BaseSavedState.EMPTY_STATE)
        savedState.bgPaintColor = bgPaint.color
        return savedState
    }

    override fun onRestoreInstanceState(state: Parcelable) {
        if (state is SavedState) {
            bgPaint.color = state.bgPaintColor
            super.onRestoreInstanceState(state.superState)
        } else {
            super.onRestoreInstanceState(state)
        }
    }

    override fun onDraw(canvas: Canvas) {
        bgPaint.style = Paint.Style.STROKE
        canvas.drawRoundRect(bgRect, frameCornerRadius, frameCornerRadius, bgPaint)
        bgPaint.style = Paint.Style.FILL_AND_STROKE
        canvas.drawPath(nameBgPath, bgPaint)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        bgRect.set(frameWidth, frameWidth, measuredWidth - frameWidth, measuredHeight - frameWidth)
        updateNamePath()
    }

    private fun updateNamePath() {
        val left = frameWidth
        val top = imageView.bottom.toFloat()
        val right = measuredWidth - frameWidth
        val bottom = measuredHeight - frameWidth
        nameBgPath.reset()
        nameBgPath.moveTo(left, top)
        nameBgPath.lineTo(right, top)
        nameBgPath.lineTo(right, bottom - frameCornerRadius)
        nameBgPath.arcTo(
            right - frameCornerRadius * 2,
            bottom - frameCornerRadius * 2,
            right,
            bottom,
            RIGHT_BOTTOM_ANGLE,
            SWEEP_ANGLE,
            false
        )
        nameBgPath.lineTo(left + frameCornerRadius, bottom)
        nameBgPath.arcTo(
            left,
            bottom - frameCornerRadius * 2,
            left + frameCornerRadius * 2,
            bottom,
            LEFT_BOTTOM_ANGLE,
            SWEEP_ANGLE,
            false
        )
        nameBgPath.lineTo(left, top)
        nameBgPath.close()
    }

    internal class SavedState : BaseSavedState {
        var bgPaintColor: Int = 0

        constructor(source: Parcel) : super(source) {
            bgPaintColor = source.readInt()
        }

        constructor(superState: Parcelable) : super(superState)

        override fun writeToParcel(out: Parcel, flags: Int) {
            super.writeToParcel(out, flags)
            out.writeInt(bgPaintColor)
        }

        companion object {
            @JvmField
            val CREATOR = object : Parcelable.Creator<SavedState> {
                override fun createFromParcel(source: Parcel): SavedState {
                    return SavedState(source)
                }

                override fun newArray(size: Int): Array<SavedState?> {
                    return arrayOfNulls(size)
                }
            }
        }
    }

    companion object {
        private const val RIGHT_BOTTOM_ANGLE = 0F
        private const val LEFT_BOTTOM_ANGLE = 90F
        private const val SWEEP_ANGLE = 90F
    }
}