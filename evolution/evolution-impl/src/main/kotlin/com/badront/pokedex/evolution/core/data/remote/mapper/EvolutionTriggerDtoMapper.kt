package com.badront.pokedex.evolution.core.data.remote.mapper

import android.net.Uri
import com.badront.pokedex.core.data.remote.model.NamedApiResourceDto
import com.badront.pokedex.evolution.core.domain.model.EvolutionTrigger
import javax.inject.Inject

internal class EvolutionTriggerDtoMapper @Inject constructor() {

    fun map(dto: NamedApiResourceDto): EvolutionTrigger {
        return EvolutionTrigger(
            id = Uri.parse(dto.url).lastPathSegment!!.toInt(),
            name = dto.name
        )
    }
}