package com.badront.pokedex.item.core.widget

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.badront.pokedex.item.api.databinding.ItemViewBinding
import com.badront.pokedex.item.core.domain.model.Item

class ItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : LinearLayout(context, attrs, defStyle) {
    private val binding = ItemViewBinding.inflate(LayoutInflater.from(context), this)
    var item: Item? = null
        set(value) {
            field = value
            binding.image.loadItem(item?.image ?: "")
            binding.name.text = item?.name
        }

    var onItemClickListener: ((Item) -> Unit)? = null

    init {
        orientation = VERTICAL
        gravity = Gravity.CENTER_HORIZONTAL
        setBackgroundResource(com.badront.pokedex.design.R.drawable.ripple_rounded_8)
        setOnClickListener {
            item?.let { onItemClickListener?.invoke(it) }
        }
    }
}