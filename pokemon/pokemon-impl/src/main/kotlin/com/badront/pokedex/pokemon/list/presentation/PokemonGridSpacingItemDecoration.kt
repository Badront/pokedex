package com.badront.pokedex.pokemon.list.presentation

import android.content.Context
import com.badront.pokedex.core.ext.android.content.getDimensionPixelOffsetKtx
import com.badront.pokedex.core.util.recycler.GridSpacingItemDecoration
import com.badront.pokedex.design.R

class PokemonGridSpacingItemDecoration(context: Context) : GridSpacingItemDecoration(
    verticalSpacing = context.getDimensionPixelOffsetKtx(R.dimen.offset_8),
    horizontalSpacing = context.getDimensionPixelOffsetKtx(R.dimen.offset_8),
    verticalEdgeSpacing = context.getDimensionPixelOffsetKtx(R.dimen.offset_8),
    horizontalEdgeSpacing = context.getDimensionPixelOffsetKtx(R.dimen.offset_8)
)