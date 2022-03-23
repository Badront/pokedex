package com.badront.pokedex.evolution.core.data.remote.model

import com.badront.pokedex.core.data.remote.model.NamedApiResourceDto
import com.google.gson.annotations.SerializedName

class EvolutionChainLinkDto(
    @SerializedName("is_baby")
    val isBaby: Boolean,
    @SerializedName("species")
    val species: NamedApiResourceDto,
    @SerializedName("evolution_details")
    val details: EvolutionDetailsDto? = null,
    @SerializedName("evolves_to")
    val evolvesTo: List<EvolutionChainLinkDto>
)