package com.badront.pokedex.evolution.core.data.remote.model

import com.badront.pokedex.core.data.remote.model.NamedApiResourceDto
import com.google.gson.annotations.SerializedName

internal class EvolutionDetailsDto(
    // The item required to cause evolution this into Pokémon species
    @SerializedName("item")
    val item: NamedApiResourceDto? = null,
    // The type of event that triggers evolution into this Pokémon species
    @SerializedName("trigger")
    val trigger: NamedApiResourceDto? = null,
    // The id of the gender of the evolving Pokémon species must be in order to evolve into this Pokémon species
    @SerializedName("gender")
    val gender: Int? = null,
    // The item the evolving Pokémon species must be holding during the evolution trigger event to evolve into this Pokémon species
    @SerializedName("held_item")
    val heldItem: NamedApiResourceDto? = null,
    // The move that must be known by the evolving Pokémon species during the evolution trigger event in order to evolve into this Pokémon species
    @SerializedName("known_move")
    val knownMove: NamedApiResourceDto? = null,
    // The evolving Pokémon species must know a move with this type during the evolution trigger event in order to evolve into this Pokémon species
    @SerializedName("known_move_type")
    val knownMoveType: NamedApiResourceDto? = null,
    // The location the evolution must be triggered at
    @SerializedName("location")
    val location: NamedApiResourceDto? = null,
    // The minimum required level of the evolving Pokémon species to evolve into this Pokémon species
    @SerializedName("min_level")
    val minLevel: Int? = null,
    // The minimum required level of happiness the evolving Pokémon species to evolve into this Pokémon species
    @SerializedName("min_happiness")
    val minHappiness: Int? = null,
    // The minimum required level of beauty the evolving Pokémon species to evolve into this Pokémon species
    @SerializedName("min_beauty")
    val minBeauty: Int? = null,
    // The minimum required level of affection the evolving Pokémon species to evolve into this Pokémon species
    @SerializedName("min_affection")
    val minAffection: Int? = null,
    // Whether or not it must be raining in the overworld to cause evolution this Pokémon species
    @SerializedName("needs_overworld_rain")
    val needOverworldRain: Boolean = false,
    // The Pokémon species that must be in the players party in order for the evolving Pokémon species to evolve into this Pokémon species
    @SerializedName("party_species")
    val partySpecies: NamedApiResourceDto? = null,
    // The player must have a Pokémon of this type in their party during the evolution trigger event in order for the evolving Pokémon species to evolve into this Pokémon species
    @SerializedName("party_type")
    val partyType: NamedApiResourceDto? = null,
    // The required relation between the Pokémon's Attack and Defense stats. 1 means Attack > Defense. 0 means Attack = Defense. -1 means Attack < Defense
    @SerializedName("relative_physical_stats")
    val relativePhysicalStats: Int? = null,
    // The required time of day. Day or night
    @SerializedName("time_of_day")
    val timeOfDay: String? = null,
    // Pokémon species for which this one must be traded
    @SerializedName("trade_species")
    val tradeSpecies: NamedApiResourceDto? = null,
    // Whether or not the 3DS needs to be turned upside-down as this Pokémon levels up
    @SerializedName("turn_upside_down")
    val turnUpsideDown: Boolean = false
)