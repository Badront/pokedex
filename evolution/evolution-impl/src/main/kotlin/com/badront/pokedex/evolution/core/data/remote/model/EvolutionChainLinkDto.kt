package com.badront.pokedex.evolution.core.data.remote.model

import com.badront.pokedex.core.data.remote.model.NamedApiResourceDto
import com.google.gson.annotations.SerializedName

internal class EvolutionChainLinkDto(
    // Whether or not this link is for a baby Pokémon. This would only ever be true on the base link
    @SerializedName("is_baby")
    val isBaby: Boolean,
    // The Pokémon species at this point in the evolution chain
    @SerializedName("species")
    val species: NamedApiResourceDto,
    // All details regarding the specific details of the referenced Pokémon species evolution
    @SerializedName("evolution_details")
    val details: List<EvolutionDetailsDto>? = null,
    // A List of chain objects
    @SerializedName("evolves_to")
    val evolvesTo: List<EvolutionChainLinkDto>
)