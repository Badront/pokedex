package com.badront.pokedex.evolution.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
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
import com.badront.pokedex.item.core.domain.model.Item
import com.badront.pokedex.pokemon.core.domain.model.Pokemon
import com.badront.pokedex.pokemon.core.widget.PokemonView

class EvolutionView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : ViewGroup(context, attrs, defStyle) {
    private val pokemonViewMap = mutableMapOf<Pokemon, PokemonView>()
    private val chainViewMap = mutableMapOf<EvolutionChain, EvolutionDetailsView>()
    private val depthCount = mutableMapOf<Int, Int>()
    private val arrows = mutableListOf<Path>()

    private val arrowPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeJoin = Paint.Join.ROUND
        strokeCap = Paint.Cap.ROUND
    }
    private val arrowHeadX = context.getDimensionPixelSizeKtx(R.dimen.evolution_arrow_head_x)
    private val arrowHeadY = context.getDimensionPixelSizeKtx(R.dimen.evolution_arrow_head_y)
    private val arrowMultipleEvolutionTail = context.getDimensionPixelOffsetKtx(R.dimen.evolution_multiple_arrow_tail)
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
            getPokemonChildren().forEach {
                it.onPokemonClickListener = onPokemonClick
            }
        }
    var onItemClick: ((Item) -> Unit)? = null
        set(value) {
            field = value
            getDetailsChildren().forEach {
                it.onItemClickListener = onItemClick
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

    override fun onDraw(canvas: Canvas) {
        arrows.forEach { arrow ->
            canvas.drawPath(arrow, arrowPaint)
        }
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        arrows.clear()
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

    private fun layoutChain(chain: EvolutionChain, left: Int, top: Int, right: Int, bottom: Int): PokemonView? {
        return pokemonViewMap[chain.pokemon]?.let { pokemonView ->
            val viewTop = top + (bottom - top) / 2 - pokemonView.measuredHeight / 2
            val viewRight = left + pokemonView.measuredWidth
            val viewBottom = viewTop + pokemonView.measuredHeight
            pokemonView.layout(
                left,
                viewTop,
                viewRight,
                viewBottom
            )
            if (chain.evolvesTo.isNotEmpty()) {
                val chainHeight = (bottom - top) / chain.evolvesTo.size
                chain.evolvesTo.forEachIndexed { index, childChain ->
                    val viewWithArrowRight = viewRight + getArrowLengthTo(pokemonView)
                    val viewPartTop = top + chainHeight * index
                    layoutChain(
                        childChain,
                        viewWithArrowRight,
                        viewPartTop,
                        right,
                        viewPartTop + chainHeight
                    )?.let { evolvedPokemonView ->
                        addEvolveArrow(pokemonView, evolvedPokemonView)
                        layoutDetails(
                            childChain,
                            pokemonView,
                            evolvedPokemonView
                        )
                    }
                }
            }
            pokemonView
        }
    }

    private fun addEvolveArrow(pokemonView: PokemonView, evolvedPokemonView: PokemonView) {
        val pokemonViewCenterY = (pokemonView.top + (pokemonView.bottom - pokemonView.top) / 2).toFloat()
        val evolvedPokemonViewCenterY =
            (evolvedPokemonView.top + (evolvedPokemonView.bottom - evolvedPokemonView.top) / 2).toFloat()
        val arrowPath = Path().apply {
            fillType = Path.FillType.EVEN_ODD
        }
        arrowPath.moveTo(
            pokemonView.right.toFloat(),
            pokemonViewCenterY
        )
        if (evolvedPokemonView.top != pokemonVerticalPadding) {
            arrowPath.lineTo(
                pokemonView.right.toFloat() + arrowMultipleEvolutionTail,
                pokemonViewCenterY
            )
            arrowPath.lineTo(
                pokemonView.right.toFloat() + arrowMultipleEvolutionTail,
                evolvedPokemonViewCenterY
            )
        }
        arrowPath.lineTo(
            evolvedPokemonView.left.toFloat(),
            evolvedPokemonViewCenterY
        )
        arrowPath.moveTo(
            (evolvedPokemonView.left - arrowHeadX).toFloat(),
            evolvedPokemonViewCenterY - arrowHeadY
        )
        arrowPath.lineTo(
            evolvedPokemonView.left.toFloat(),
            evolvedPokemonViewCenterY
        )
        arrowPath.moveTo(
            (evolvedPokemonView.left - arrowHeadX).toFloat(),
            evolvedPokemonViewCenterY + arrowHeadY
        )
        arrowPath.lineTo(
            evolvedPokemonView.left.toFloat(),
            evolvedPokemonViewCenterY
        )
        arrows.add(arrowPath)
    }

    private fun layoutDetails(chain: EvolutionChain, pokemonView: PokemonView, evolvedPokemonView: PokemonView) {
        chainViewMap[chain]?.let { detailsView ->
            val evolvedCenterY = evolvedPokemonView.top + (evolvedPokemonView.bottom - evolvedPokemonView.top) / 2
            val detailsViewTop = evolvedCenterY - detailsView.measuredHeight
            detailsView.layout(
                pokemonView.right,
                detailsViewTop,
                evolvedPokemonView.left,
                evolvedCenterY
            )
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
        return view.measuredWidth
    }

    private fun addDepthCount(depth: Int, chain: EvolutionChain) {
        depthCount[depth] = (depthCount[depth] ?: 0) + 1
        chain.evolvesTo.forEach { childChain ->
            addDepthCount(depth + 1, childChain)
        }
    }

    private fun updateWidgets() {
        detachAllPokemonViews()
        detachAllDetailViews()
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
        if (chain.details.isNotEmpty()) {
            val detailViews = getDetailsChildren()
            val detailsView = if (chainViewMap.size < detailViews.count()) {
                detailViews.first { detailView ->
                    chainViewMap.values.none { it.id == detailView.id }
                }
            } else {
                createDetailsView().also {
                    addView(it)
                }
            }
            detailsView.bringToFront()
            attachViewToChain(detailsView, chain)
        }
        chain.evolvesTo.forEach { childChain ->
            attachChain(childChain)
        }
    }

    private fun detachAllPokemonViews() {
        pokemonViewMap.clear()
    }

    private fun detachAllDetailViews() {
        chainViewMap.clear()
    }

    private fun getPokemonChildren(): Sequence<PokemonView> {
        return children.filterIsInstance<PokemonView>()
    }

    private fun getDetailsChildren(): Sequence<EvolutionDetailsView> {
        return children.filterIsInstance<EvolutionDetailsView>()
    }

    private fun createPokemonView(): PokemonView {
        return PokemonView(context).apply {
            id = View.generateViewId()
            onPokemonClickListener = onPokemonClick
        }
    }

    private fun createDetailsView(): EvolutionDetailsView {
        return EvolutionDetailsView(context).apply {
            id = View.generateViewId()
            onItemClickListener = onItemClick
        }
    }

    override fun onViewRemoved(child: View?) {
        when (child) {
            is PokemonView -> detachViewFromPokemon(child)
            is EvolutionDetailsView -> detachViewFromChain(child)
        }
        super.onViewRemoved(child)
    }

    override fun onAttachedToWindow() {
        getPokemonChildren().forEach { pokemonView ->
            pokemonView.pokemon?.let { pokemon ->
                pokemonViewMap[pokemon] = pokemonView
            }
        }
        evolutionChain?.let { chain ->
            chain.evolvesTo.forEach {
                rebindChainToDetailsView(it)
            }
        }
        super.onAttachedToWindow()
    }

    override fun onDetachedFromWindow() {
        pokemonViewMap.clear()
        chainViewMap.clear()
        super.onDetachedFromWindow()
    }

    private fun rebindChainToDetailsView(chain: EvolutionChain) {
        getDetailsChildren().find {
            it.details == chain.details
        }?.let { view ->
            chainViewMap[chain] = view
        }
        chain.evolvesTo.forEach {
            rebindChainToDetailsView(it)
        }
    }

    private fun attachViewToChain(view: EvolutionDetailsView, chain: EvolutionChain) {
        detachViewFromChain(view)
        chainViewMap[chain] = view
        view.details = chain.details
    }

    private fun detachViewFromChain(view: EvolutionDetailsView) {
        chainViewMap
            .filterValues { it.id == view.id }
            .forEach {
                chainViewMap.remove(it.key)
            }
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