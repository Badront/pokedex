package com.badront.pokedex.pokemon.details.presentation.model

import com.badront.pokedex.pokemon.core.domain.model.Pokemon
import java.util.Locale

internal val Pokemon.uiName
    get() = name.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
internal val Pokemon.uiNumber
    get() = "#${number.toString().padStart(4, '0')}"