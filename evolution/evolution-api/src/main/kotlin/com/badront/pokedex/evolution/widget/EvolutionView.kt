package com.badront.pokedex.evolution.widget

import android.content.Context
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import com.badront.pokedex.core.ext.android.content.getColorKtx
import com.badront.pokedex.core.ext.android.content.getDimensionPixelOffsetKtx
import com.badront.pokedex.core.ext.android.content.getDimensionPixelSizeKtx
import com.badront.pokedex.core.ext.android.view.measureDimension
import com.badront.pokedex.evolution.core.domain.model.EvolutionChain
import com.badront.pokedex.evolution.impl.R
import com.badront.pokedex.pokemon.core.domain.model.Pokemon
import com.badront.pokedex.pokemon.core.widget.PokemonView

class EvolutionView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : ViewGroup(context, attrs, defStyle) {
    private val pokemonViewMap = mutableMapOf<Pokemon, PokemonView>()
    private val depthCount = mutableMapOf<Int, Int>()

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
    var evolutionChain: EvolutionChain? = null
        set(value) {
            if (value != field) {
                field = value
                updateWidgets()
            }
        }
    var onPokemonClick: ((Pokemon) -> Unit)? = null
        set(value) {
            field = value
            children.filterIsInstance(PokemonView::class.java).forEach {
                it.onPokemonClickListener = onPokemonClick
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
        evolutionChain?.let { chain ->
            pokemonViewMap[chain.pokemon]?.let { view ->
                val maxRowSize = depthCount.size
                val pokemonViewWidth = view.measuredWidth
                val arrowWidth = getArrowLengthTo(view)
                val expectedWidth = pokemonViewWidth * maxRowSize + arrowWidth * (maxRowSize - 1)
                val firstViewLeft = parentLeft + (parentRight - parentLeft) / 2 - expectedWidth / 2
                layoutChain(chain, firstViewLeft, parentTop, parentRight, parentBottom)
            }
        }
    }

    private fun layoutChain(chain: EvolutionChain, left: Int, top: Int, right: Int, bottom: Int) {
        pokemonViewMap[chain.pokemon]?.let { view ->
            val viewTop = top + (bottom - top) / 2 - view.measuredHeight / 2
            val viewRight = left + view.measuredWidth
            val viewBottom = viewTop + view.measuredHeight
            view.layout(
                left,
                viewTop,
                viewRight,
                viewBottom
            )
            if (chain.evolvedTo.isNotEmpty()) {
                val chainHeight = (bottom - top) / chain.evolvedTo.size
                chain.evolvedTo.forEachIndexed { index, childChain ->
                    val viewWithArrowRight = viewRight + getArrowLengthTo(view)
                    val viewPartTop = top + chainHeight * index
                    layoutChain(
                        childChain,
                        viewWithArrowRight,
                        viewPartTop,
                        right,
                        viewPartTop + chainHeight
                    )
                }
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        depthCount.clear()
        evolutionChain?.let { chain ->
            addDepthCount(0, chain)
        }
        children.forEach { child ->
            measureChild(child, widthMeasureSpec, heightMeasureSpec)
        }

        var maxColumnSize = 0
        val maxColumnDepths = mutableListOf<Int>()
        depthCount.forEach { (depth, count) ->
            when {
                maxColumnSize < count -> {
                    maxColumnSize = count
                    maxColumnDepths.clear()
                    maxColumnDepths.add(depth)
                }
                maxColumnSize == count -> {
                    maxColumnDepths.add(depth)
                }
            }
        }
        val maxRowSize = depthCount.size
        var maxHeight = 0
        var maxWidth = 0
        getPokemonChildren().firstOrNull()?.let { pokemonView ->
            maxHeight = maxColumnSize * pokemonView.measuredHeight + (maxColumnSize - 1) * pokemonVerticalPadding
            maxWidth = maxRowSize * pokemonView.measuredWidth + (maxColumnSize - 1) * getArrowLengthTo(pokemonView)
        }
        setMeasuredDimension(
            measureDimension(maxWidth + paddingLeft + paddingRight, widthMeasureSpec),
            measureDimension(maxHeight + paddingTop + paddingBottom, heightMeasureSpec)
        )
    }

    private fun getArrowLengthTo(view: PokemonView): Int {
        // todo высчитывать длину стрелки
        return view.measuredWidth
    }

    private fun addDepthCount(depth: Int, chain: EvolutionChain) {
        depthCount[depth] = (depthCount[depth] ?: 0) + 1
        chain.evolvedTo.forEach { childChain ->
            addDepthCount(depth + 1, childChain)
        }
    }

    private fun updateWidgets() {
        detachAllPokemonViews()
        evolutionChain?.let {
            attachChain(it)
        }
        invalidate()
    }

    private fun attachChain(chain: EvolutionChain) {
        val pokeViews = getPokemonChildren()
        val pokemonView = if (pokemonViewMap.size < pokeViews.count()) {
            pokeViews.first { pokeView ->
                pokemonViewMap.values.none { it.id == pokeView.id }
            }
        } else {
            createPokemonView().also {
                addView(it)
            }
        }
        pokemonView.bringToFront()
        attachViewToPokemon(pokemonView, chain.pokemon)
        chain.evolvedTo.forEach { childChain ->
            attachChain(childChain)
        }
    }

    private fun detachAllPokemonViews() {
        pokemonViewMap.clear()
    }

    private fun getPokemonChildren(): Sequence<PokemonView> {
        return children.filterIsInstance<PokemonView>()
    }

    private fun createPokemonView(): PokemonView {
        return PokemonView(context).apply {
            id = View.generateViewId()
            onPokemonClickListener = onPokemonClick
        }
    }

    override fun onViewRemoved(child: View?) {
        if (child is PokemonView) {
            detachViewFromPokemon(child)
        }
        super.onViewRemoved(child)
    }

    private fun attachViewToPokemon(view: PokemonView, pokemon: Pokemon) {
        detachViewFromPokemon(view)
        pokemonViewMap[pokemon] = view
        view.pokemon = pokemon
    }

    private fun detachViewFromPokemon(child: PokemonView) {
        pokemonViewMap
            .filterValues { it.id == child.id }
            .forEach {
                pokemonViewMap.remove(it.key)
            }
    }
}