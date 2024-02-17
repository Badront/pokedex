package com.badront.pokedex.core.util.recycler

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Adding padding between items inside [RecyclerView] with [GridLayoutManager]
 * @param horizontalSpacing - padding between items of a row. Half before item and half after.
 * @param verticalSpacing - padding between items of a column. Half before item and half after.
 * @param verticalEdgeSpacing - padding between last\first item in column and parent.
 * @param horizontalEdgeSpacing - padding between last\first item in row and parent.
 * @param spacingPredicate - if padding should be applied to child at current position. Default is always true.
 */
open class GridSpacingItemDecoration(
    private val verticalSpacing: Int,
    private val horizontalSpacing: Int,
    private val verticalEdgeSpacing: Int = 0,
    private val horizontalEdgeSpacing: Int = 0,
    protected val spacingPredicate: (position: Int) -> Boolean = { true }
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val layoutManager = parent.layoutManager as? GridLayoutManager ?: error("Use with GridLayoutManager only")
        val position = parent.getChildAdapterPosition(view)
        if (position == RecyclerView.NO_POSITION) return

        if (spacingPredicate(position)) {
            addItemSpacing(parent, layoutManager, position, outRect)
        }
    }

    private fun addItemSpacing(
        parent: RecyclerView,
        layoutManager: GridLayoutManager,
        position: Int,
        outRect: Rect
    ) {
        val spanCount = layoutManager.spanCount
        val spanSizeLookup = layoutManager.spanSizeLookup

        val itemSpanIndex = spanSizeLookup.getSpanIndex(position, spanCount)
        if (itemSpanIndex != 0) {
            outRect.left = horizontalSpacing / 2
        } else {
            outRect.left = horizontalEdgeSpacing
        }
        val nextItemSpanIndex = spanSizeLookup.getSpanIndex(position + 1, spanCount)
        val isLastInRow = nextItemSpanIndex == 0
        val itemSpanSize = spanSizeLookup.getSpanSize(position)
        when {
            isLastInRow.not() -> {
                outRect.right = horizontalSpacing / 2
            }
            itemSpanIndex + itemSpanSize < spanCount -> {
                outRect.right = horizontalSpacing / 2
            }
            else -> {
                outRect.right = horizontalEdgeSpacing
            }
        }
        val itemRowIndex = spanSizeLookup.getSpanGroupIndex(position, spanCount)
        if (itemRowIndex != 0) {
            outRect.top = verticalSpacing / 2
        } else {
            outRect.top = verticalEdgeSpacing
        }
        val lastItemRowIndex = spanSizeLookup.getSpanGroupIndex(
            parent.adapter?.itemCount ?: position,
            spanCount
        )
        val isInLastRow = lastItemRowIndex == itemRowIndex
        if (!isInLastRow) {
            outRect.bottom = verticalSpacing / 2
        } else {
            outRect.bottom = verticalEdgeSpacing
        }
    }
}