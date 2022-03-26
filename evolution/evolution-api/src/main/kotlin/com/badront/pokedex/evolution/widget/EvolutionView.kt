package com.badront.pokedex.evolution.widget

import android.content.Context
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import com.badront.pokedex.core.ext.android.content.getColorKtx
import com.badront.pokedex.core.ext.android.content.getDimensionPixelOffsetKtx
import com.badront.pokedex.core.ext.android.content.getDimensionPixelSizeKtx
import com.badront.pokedex.evolution.impl.R
import com.badront.pokedex.evolution.widget.model.EvolutionUi
import com.badront.pokedex.pokemon.core.widget.loadPokemon

class EvolutionView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : ViewGroup(context, attrs, defStyle) {
    private val pokemonFromMap = mutableMapOf<Int, Int>()
    private val pokemonToMap = mutableMapOf<Int, Int>()
    private var currentPokemonViewId: Int? = null

    private val arrowPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var arrowPadding: Int
    private var pokemonSize: Int
    var evolution: EvolutionUi? = null
        set(value) {
            if (value != field) {
                field = value
                updateWidgets()
            }
        }

    init {
        val array =
            context.obtainStyledAttributes(attrs, R.styleable.EvolutionView, defStyle, R.style.EvolutionViewStyle)
        arrowPaint.color = array.getColor(
            R.styleable.EvolutionView_ev_arrowColor,
            context.getColorKtx(com.badront.pokedex.design.R.color.wireframe)
        )
        arrowPaint.strokeWidth = array.getDimensionPixelSize(
            R.styleable.EvolutionView_ev_arrowWidth,
            context.getDimensionPixelSizeKtx(com.badront.pokedex.design.R.dimen.default_stroke_width)
        ).toFloat()
        arrowPadding = array.getDimensionPixelOffset(
            R.styleable.EvolutionView_ev_arrowPadding,
            context.getDimensionPixelOffsetKtx(com.badront.pokedex.design.R.dimen.offset_4)
        )
        pokemonSize = array.getDimensionPixelSize(
            R.styleable.EvolutionView_ev_pokemonSize,
            context.getDimensionPixelSizeKtx(com.badront.pokedex.pokemon.api.R.dimen.default_pokemon_size)
        )
        array.recycle()
        setWillNotDraw(false)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        TODO("Not yet implemented")
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    private fun updateWidgets() {
        val currentEvolution = evolution
        if (currentEvolution != null) {
            updatePokemons(
                pokemons = currentEvolution.from,
                positionViewMap = pokemonFromMap,
                createView = ::createPokemonViewFrom,
                getView = ::getPokemonViewFrom
            )
            updatePokemons(
                pokemons = currentEvolution.to,
                positionViewMap = pokemonToMap,
                createView = ::createPokemonViewTo,
                getView = ::getPokemonViewTo
            )
        } else {
            removeAllViews()
        }
    }

    private inline fun updatePokemons(
        pokemons: List<EvolutionUi.Link>,
        positionViewMap: MutableMap<Int, Int>,
        createView: (position: Int) -> ImageView,
        getView: (position: Int) -> ImageView?
    ) {
        val wasCount = positionViewMap.size
        val newCount = pokemons.size
        when {
            newCount == 0 -> {
                positionViewMap.forEach { (_, viewId) ->
                    removeView(findViewById(viewId))
                }
            }
            wasCount > newCount -> {
                (newCount until wasCount).forEach { position ->
                    positionViewMap[position]?.let { viewId ->
                        removeView(findViewById(viewId))
                    }
                }
            }
            wasCount < newCount -> {
                (wasCount until newCount).forEach { position ->
                    addView(createView(position), getPokemonViewLayoutParams())
                }
            }
        }
        pokemons.forEachIndexed { index, link ->
            getView(index)?.apply {
                loadPokemon(link.pokemon.image)
            }
        }
    }

    private fun getPokemonViewFrom(position: Int): ImageView? {
        return pokemonFromMap[position]?.let { viewId ->
            findViewById(viewId)
        }
    }

    private fun createPokemonViewFrom(position: Int): ImageView {
        return createPokemonView().also {
            pokemonFromMap[position] = it.id
        }
    }

    private fun getPokemonViewTo(position: Int): ImageView? {
        return pokemonToMap[position]?.let { viewId ->
            findViewById(viewId)
        }
    }

    private fun createPokemonViewTo(position: Int): ImageView {
        return createPokemonView().also {
            pokemonToMap[position] = it.id
        }
    }

    private fun createPokemonView(): ImageView {
        return AppCompatImageView(context).apply {
            id = View.generateViewId()
            scaleType = ImageView.ScaleType.FIT_CENTER
        }
    }

    private fun getPokemonViewLayoutParams(): LayoutParams {
        return LayoutParams(
            pokemonSize,
            pokemonSize
        )
    }
}