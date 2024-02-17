package com.badront.pokedex.core.ext.androidx.palette.graphics

import android.os.Parcelable
import androidx.annotation.ColorInt
import kotlinx.parcelize.Parcelize

@Parcelize
data class ColorPalette(
    @ColorInt val primaryColor: Int,
    @ColorInt val onPrimaryColor: Int
) : Parcelable