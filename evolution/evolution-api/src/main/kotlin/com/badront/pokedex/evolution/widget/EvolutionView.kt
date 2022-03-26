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
import com.badront.pokedex.core.ext.android.view.measureDimension
import com.badront.pokedex.evolution.impl.R
import com.badront.pokedex.evolution.widget.model.EvolutionUi
import com.badront.pokedex.pokemon.core.domain.model.Pokemon
import com.badront.pokedex.pokemon.core.widget.loadPokemon
import kotlin.math.max

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
    private var pokemonVerticalPadding: Int = 0
        set(value) {
            if (field != value) {
                field = value
                invalidate()
            }
        }
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
        pokemonVerticalPadding = array.getDimensionPixelOffset(
            R.styleable.EvolutionView_ev_pokemonVerticalPadding,
            context.getDimensionPixelOffsetKtx(com.badront.pokedex.design.R.dimen.offset_8)
        )
        array.recycle()
        setWillNotDraw(false)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val parentLeft = paddingLeft
        val parentTop = paddingTop
        val parentRight = measuredWidth - paddingRight
        val parentBottom = measuredHeight - paddingBottom
        var currentLeft = parentLeft
        val fromPokemons = getPokemonFromViews()
        var maxPokemonsHeight = 0
        var maxPokemonWidth = 0
        fromPokemons.forEach { child ->
            maxPokemonsHeight += child.measuredHeight
            maxPokemonWidth = max(maxPokemonWidth, child.measuredWidth)
        }
        maxPokemonsHeight += (fromPokemons.size - 1) * pokemonVerticalPadding
        var currentTop = parentTop + (parentBottom - parentTop - maxPokemonsHeight) / 2
        var currentRight = currentLeft + maxPokemonWidth
        fromPokemons.forEach { child ->
            val centerX = currentLeft + (currentRight - currentLeft) / 2
            child.layout(
                centerX - child.measuredWidth / 2,
                currentTop,
                centerX + child.measuredWidth / 2,
                currentTop + child.measuredHeight
            )
            currentTop += child.measuredHeight + pokemonVerticalPadding
        }
        currentPokemonViewId?.let { viewId ->
            val child = findViewById<View>(viewId)
            val currentX = parentLeft + (parentRight - parentLeft) / 2
            val centerY = parentTop + (parentBottom - parentTop) / 2
            val width = child.measuredWidth
            val height = child.measuredHeight
            child.layout(
                currentX - width / 2,
                centerY - height / 2,
                currentX + width / 2,
                centerY + height / 2
            )
        }
        val toPokemons = getPokemonToViews()
        maxPokemonsHeight = 0
        maxPokemonWidth = 0
        toPokemons.forEach { child ->
            maxPokemonsHeight += child.measuredHeight
            maxPokemonWidth = max(maxPokemonWidth, child.measuredWidth)
        }
        maxPokemonsHeight += (toPokemons.size - 1) * pokemonVerticalPadding
        currentTop = parentTop + (parentBottom - parentTop - maxPokemonsHeight) / 2
        currentRight = parentRight
        currentLeft = currentRight - maxPokemonWidth
        toPokemons.forEach { child ->
            val centerX = currentLeft + (currentRight - currentLeft) / 2
            child.layout(
                centerX - child.measuredWidth / 2,
                currentTop,
                centerX + child.measuredWidth / 2,
                currentTop + child.measuredHeight
            )
            currentTop += child.measuredHeight + pokemonVerticalPadding
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var maxHeight = 0
        var fromMaxHeight = 0
        var fromMaxWidth = 0
        /**
         * measuring pokemons from which this can evolve
         */
        pokemonFromMap.forEach { (_, viewId) ->
            val child = findViewById<View>(viewId)
            measureChild(child, widthMeasureSpec, heightMeasureSpec)
            fromMaxHeight += child.measuredHeight
            fromMaxWidth = max(fromMaxWidth, child.measuredWidth)
        }
        if (pokemonFromMap.isNotEmpty()) {
            fromMaxHeight += (pokemonFromMap.size - 1) * pokemonVerticalPadding
        }
        maxHeight = max(maxHeight, fromMaxHeight)
        /**
         * measuring current pokemon
         */
        var currentPokemonMaxWidth = 0
        currentPokemonViewId?.let { viewId ->
            val child = findViewById<View>(viewId)
            measureChild(child, widthMeasureSpec, heightMeasureSpec)
            maxHeight = max(maxHeight, child.measuredHeight)
            currentPokemonMaxWidth = child.measuredWidth
        }
        /**
         * measuring pokemons to which this can evolve
         */
        var toMaxHeight = 0
        var toMaxWidth = 0
        pokemonToMap.forEach { (_, viewId) ->
            val child = findViewById<View>(viewId)
            measureChild(child, widthMeasureSpec, heightMeasureSpec)
            toMaxHeight += child.measuredHeight
            toMaxWidth = max(toMaxWidth, child.measuredWidth)
        }
        if (pokemonToMap.isNotEmpty()) {
            toMaxHeight += (pokemonToMap.size - 1) * pokemonVerticalPadding
        }
        maxHeight = max(maxHeight, toMaxHeight)

        val maxWidth = fromMaxWidth + currentPokemonMaxWidth + toMaxWidth
        setMeasuredDimension(
            measureDimension(maxWidth + paddingLeft + paddingRight, widthMeasureSpec),
            measureDimension(maxHeight + paddingTop + paddingBottom, heightMeasureSpec)
        )
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
            updateCurrentPokemon(currentEvolution.pokemon)
            updatePokemons(
                pokemons = currentEvolution.to,
                positionViewMap = pokemonToMap,
                createView = ::createPokemonViewTo,
                getView = ::getPokemonViewTo
            )
        } else {
            currentPokemonViewId = null
            pokemonFromMap.clear()
            pokemonToMap.clear()
            removeAllViews()
        }
    }

    private fun getPokemonToViews(): List<View> {
        return pokemonToMap.keys.sorted().mapNotNull { position ->
            pokemonToMap[position]?.let { viewId ->
                findViewById(viewId)
            }
        }
    }

    private fun getPokemonFromViews(): List<View> {
        return pokemonFromMap.keys.sorted().mapNotNull { position ->
            pokemonFromMap[position]?.let { viewId ->
                findViewById(viewId)
            }
        }
    }

    private fun updateCurrentPokemon(pokemon: Pokemon) {
        val currentPokemonView = currentPokemonViewId?.let {
            findViewById(it)
        } ?: createPokemonView().also {
            currentPokemonViewId = it.id
            addView(it, getPokemonViewLayoutParams())
        }
        currentPokemonView.loadPokemon(pokemon.image)
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