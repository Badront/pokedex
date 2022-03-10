package com.badront.pokedex.core.ext.android.content

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.PluralsRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat

fun Context.getColorKtx(@ColorRes id: Int): Int {
    return ContextCompat.getColor(this, id)
}

fun Context.getDrawableKtx(@DrawableRes id: Int): Drawable? {
    return AppCompatResources.getDrawable(this, id)
}

fun Context.getColorStateListKtx(@ColorRes id: Int): ColorStateList {
    return AppCompatResources.getColorStateList(this, id)
}

fun Context.getQuantityStringKtx(@PluralsRes id: Int, quantity: Int, vararg values: Any?): String {
    return if (values.isNotEmpty()) {
        resources.getQuantityString(id, quantity, *values)
    } else {
        resources.getQuantityString(id, quantity, quantity)
    }
}

fun Context.getDimensionKtx(@DimenRes id: Int): Float {
    return resources.getDimension(id)
}

fun Context.getDimensionPixelSizeKtx(@DimenRes id: Int): Int {
    return resources.getDimensionPixelSize(id)
}

fun Context.getDimensionPixelOffsetKtx(@DimenRes id: Int): Int {
    return resources.getDimensionPixelOffset(id)
}