package com.badront.pokedex.core.ext.android.view

import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.marginBottom
import androidx.core.view.marginEnd
import androidx.core.view.marginStart
import androidx.core.view.marginTop

inline fun View.doOnApplyWindowInsets(
    crossinline block: (
        v: View,
        insets: WindowInsetsCompat,
        paddings: ViewOffsetState,
        margins: ViewOffsetState
    ) -> Unit
) {
    // Create a snapshot of the view's padding state
    val paddingState = createPaddingStateForView()
    val marginState = createMarginStateForView()
    ViewCompat.setOnApplyWindowInsetsListener(this) { v, insets ->
        block(v, insets, paddingState, marginState)
        insets
    }
    requestApplyInsetsWhenAttached()
}

fun View.requestApplyInsetsWhenAttached() {
    if (isAttachedToWindow) {
        requestApplyInsets()
    } else {
        addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
            override fun onViewAttachedToWindow(v: View) {
                v.requestApplyInsets()
            }

            override fun onViewDetachedFromWindow(v: View) = Unit
        })
    }
}

fun View.createPaddingStateForView(): ViewOffsetState {
    return ViewOffsetState(
        paddingStart,
        paddingTop,
        paddingEnd,
        paddingBottom
    )
}

fun View.createMarginStateForView(): ViewOffsetState {
    return ViewOffsetState(
        marginStart,
        marginTop,
        marginEnd,
        marginBottom
    )
}

fun View.measureDimension(size: Int, spec: Int): Int {
    val specMode = View.MeasureSpec.getMode(spec)
    val specSize = View.MeasureSpec.getSize(spec)

    return if (specMode == View.MeasureSpec.EXACTLY) {
        specSize
    } else {
        if (specMode == View.MeasureSpec.AT_MOST) {
            size.coerceAtMost(specSize)
        } else {
            size
        }
    }
}