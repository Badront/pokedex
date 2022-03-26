package com.badront.pokedex.pokemon.core.widget

import android.widget.ImageView
import coil.load
import coil.request.Disposable
import coil.request.ImageRequest
import com.badront.pokedex.pokemon.api.R

inline fun ImageView.loadPokemon(image: String, builder: ImageRequest.Builder.() -> Unit = {}): Disposable {
    return load(image) {
        placeholder(R.drawable.pokemon_egg)
        error(R.drawable.pokemon_empty)
        crossfade(true)
        builder(this)
    }
}