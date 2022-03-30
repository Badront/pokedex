package com.badront.pokedex.evolution.widget

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.view.ContextThemeWrapper
import com.badront.pokedex.evolution.core.domain.model.EvolutionDetails
import com.google.android.material.textview.MaterialTextView

class EvolutionTriggerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : MaterialTextView(
    ContextThemeWrapper(context, com.badront.pokedex.design.R.style.Text_Regular_8),
    attrs,
    defStyle
) {
    var details: List<EvolutionDetails> = emptyList()
        set(value) {
            if (field != value) {
                field = value
                text = value.firstOrNull()?.trigger?.name
            }
        }

    init {
        textAlignment = TEXT_ALIGNMENT_CENTER
        gravity = Gravity.CENTER
    }
}