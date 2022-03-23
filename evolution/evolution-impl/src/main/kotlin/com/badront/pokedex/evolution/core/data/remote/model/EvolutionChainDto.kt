package com.badront.pokedex.evolution.core.data.remote.model

import com.google.gson.annotations.SerializedName

class EvolutionChainDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("baby_trigger_item")
    val babyTriggerItem: BabyTriggerItemDto? = null,
    @SerializedName("chain")
    val chainLinkDto: EvolutionChainLinkDto
)