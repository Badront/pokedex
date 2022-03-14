package com.badront.pokedex.core.util.recycler

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import timber.log.Timber

/**
 * Adding padding between items inside [RecyclerView] with [LinearLayoutManager]
 * @param betweenItems - padding between items.
 * @param paddingLeft - padding to the left of the first item if orientation = HORIZONTAL and to the left of each item
 * if orientation = VERTICAL.
 * @param paddingTop - top padding to the top of every item if orientation = HORIZTONAL and to the top of first item
 * if orientation = VERTICAL.
 * @param paddingRight - padding to the right of the first item if orientation = HORIZONTAL and to the right of each
 * item if orientation = VERTICAL.
 * @param paddingBottom - bottom padding to the bottom of every item if orientation = HORIZTONAL and to the bottom
 * of first item if orientation = VERTICAL.
 * @param orientation - [RecyclerView] orientation.
 * @param spacingPredicate -  if padding should be applied to child at current position. Default is always true.
 * @sample LinearSpacingItemDecoration(3,10, 5, 10, 5, RecyclerView.HORIZONTAL) - will add 10px to the left of first item,
 * 10px after last, 5 pixels above and below each item and 3 pixels between items.
 */
open class LinearSpacingItemDecoration(
    betweenItems: Int,
    protected val paddingLeft: Int = 0,
    protected val paddingTop: Int = 0,
    protected val paddingRight: Int = 0,
    protected val paddingBottom: Int = 0,
    @RecyclerView.Orientation protected val orientation: Int = RecyclerView.HORIZONTAL,
    protected val spacingPredicate: (position: Int) -> Boolean = { true }
) : RecyclerView.ItemDecoration() {

    constructor(
        betweenItems: Int,
        padding: Int = 0,
        orientation: Int = RecyclerView.HORIZONTAL
    ) : this(
        betweenItems,
        padding,
        padding,
        padding,
        padding,
        orientation
    )

    private val spacingBeforeItem = betweenItems / 2
    private val spacingAfterItem = betweenItems - spacingBeforeItem

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view)
        if (position == RecyclerView.NO_POSITION) {
            Timber.tag(javaClass.simpleName).e("No position for item in RecyclerView")
            return
        }
        if (spacingPredicate(position)) {
            when (orientation) {
                RecyclerView.HORIZONTAL -> addHorizontalSpacing(position, state, outRect)
                RecyclerView.VERTICAL -> addVerticalSpacing(position, state, outRect)
            }
        }
    }

    private fun addHorizontalSpacing(position: Int, state: RecyclerView.State, outRect: Rect) {
        outRect.left = spacingBeforeItem
        outRect.top = paddingTop
        outRect.right = spacingAfterItem
        outRect.bottom = paddingBottom
        when (position) {
            FIRST_POSITION -> {
                outRect.left = paddingLeft
            }
            state.itemCount - 1 -> {
                outRect.right = paddingRight
            }
        }
    }

    private fun addVerticalSpacing(position: Int, state: RecyclerView.State, outRect: Rect) {
        outRect.left = paddingLeft
        outRect.top = spacingBeforeItem
        outRect.right = paddingRight
        outRect.bottom = spacingAfterItem
        when (position) {
            FIRST_POSITION -> {
                outRect.top = paddingTop
            }
            state.itemCount - 1 -> {
                outRect.bottom = paddingBottom
            }
        }
    }

    companion object {
        protected const val FIRST_POSITION = 0
    }
}