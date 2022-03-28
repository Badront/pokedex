package com.badront.pokedex.item.core.widget

import android.widget.ImageView
import coil.load
import coil.request.Disposable
import coil.request.ImageRequest
import com.badront.pokedex.item.api.R

inline fun ImageView.loadItem(image: String, builder: ImageRequest.Builder.() -> Unit = {}): Disposable {
    return load(image) {
        placeholder(R.drawable.item_unknown)
        error(R.drawable.item_unknown)
        crossfade(true)
        transitionName = image
        builder(this)
    }
}