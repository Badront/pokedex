package com.badront.pokedex.evolution.core.data.remote.model

import com.badront.pokedex.core.data.remote.model.NamedApiResourceDto
import com.google.gson.annotations.SerializedName

internal class EvolutionChainDto(
    @SerializedName("id")
    val id: Int,
    // The item that a Pokémon would be holding when mating that would trigger the egg hatching a baby Pokémon rather than a basic Pokémon
    @SerializedName("baby_trigger_item")
    val babyTriggerItem: NamedApiResourceDto? = null,
    // The base chain link object. Each link contains evolution details for a Pokémon in the chain. Each link references the next Pokémon in the natural evolution order
    @SerializedName("chain")
    val chainLinkDto: EvolutionChainLinkDto
)