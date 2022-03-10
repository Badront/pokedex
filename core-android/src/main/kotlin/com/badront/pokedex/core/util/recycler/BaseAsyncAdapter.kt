package com.badront.pokedex.core.util.recycler

import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAsyncAdapter<T, VH : RecyclerView.ViewHolder>(
    diffCallback: DiffUtil.ItemCallback<T>
) : RecyclerView.Adapter<VH>() {
    private val differ: AsyncListDiffer<T> = AsyncListDiffer(this, diffCallback)

    open fun getItems(): List<T> = differ.currentList
    open fun setItems(items: List<T>) = differ.submitList(items)
    open fun getItem(position: Int): T = getItems()[position]

    /**
     * Updated current list with new items. Runs [block] on update finish.
     * @param items list of new adapter items
     * @param block callback function to call after adapter update
     */
    open fun setItems(items: List<T>, block: () -> Unit) {
        differ.submitList(items, block)
    }

    override fun getItemCount(): Int = differ.currentList.size
}