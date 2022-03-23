package com.badront.pokedex.pokemon.core.data.remote.model

import com.badront.pokedex.core.data.remote.model.NamedApiResourceDto
import com.google.gson.annotations.SerializedName

class PokemonSpeciesDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("gender_rate")
    val genderRate: Int,
    @SerializedName("is_baby")
    val isBaby: Boolean,
    @SerializedName("is_legendary")
    val isLegendary: Boolean,
    @SerializedName("is_mythical")
    val isMythical: Boolean,
    @SerializedName("has_gender_differences")
    val hasGenderDifferences: Boolean,
    @SerializedName("forms_switchable")
    val formSwitchable: Boolean,
    @SerializedName("evolves_from_species")
    val evolvesFromSpecies: NamedApiResourceDto,
    @SerializedName("evolution_chain")
    val evolutionChain: EvolutionChainUrlDto
) {
    class EvolutionChainUrlDto(
        @SerializedName("url")
        val url: String
    )
}