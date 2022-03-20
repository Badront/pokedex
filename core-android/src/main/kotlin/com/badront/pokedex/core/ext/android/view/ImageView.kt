package com.badront.pokedex.core.ext.android.view

import android.graphics.PorterDuff
import android.widget.ImageView
import androidx.annotation.ColorInt

fun ImageView.setTint(@ColorInt color: Int) {
    setColorFilter(color, PorterDuff.Mode.MULTIPLY)
}