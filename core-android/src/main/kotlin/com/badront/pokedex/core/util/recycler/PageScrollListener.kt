package com.badront.pokedex.core.util.recycler

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class PageScrollListener : RecyclerView.OnScrollListener() {
    private var lastVisibleItemPosition: Int = 0
    abstract val itemsBeforeLoad: Int
    abstract fun isPageLoading(): Boolean
    abstract fun loadNextPage()

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        val layoutManager = recyclerView.layoutManager
        // GridLayoutManager is also LinearLayoutManager
        if (isPageLoading().not() && layoutManager is LinearLayoutManager) {
            val itemCount = layoutManager.itemCount
            val newLastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
            if (lastVisibleItemPosition < newLastVisibleItemPosition &&
                itemCount - newLastVisibleItemPosition <= itemsBeforeLoad
            ) {
                lastVisibleItemPosition = newLastVisibleItemPosition
                loadNextPage()
            }
        }
    }
}