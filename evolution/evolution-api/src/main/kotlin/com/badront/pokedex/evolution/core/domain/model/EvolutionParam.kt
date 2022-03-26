package com.badront.pokedex.evolution.core.domain.model

import com.badront.pokedex.item.core.domain.model.Item
import com.badront.pokedex.pokemon.core.domain.model.Pokemon
import com.badront.pokedex.pokemon.core.domain.model.PokemonType

sealed class EvolutionParam {
    sealed class Gender : EvolutionParam() {
        object Male : Gender()
        object Female : Gender()
    }

    class ItemParam(val item: Item) : EvolutionParam()
    class HeldItemParam(val item: Item) : EvolutionParam()
    class MoveParam(val id: Int, val name: String) : EvolutionParam()
    class MoveTypeParam(val id: Int, val name: String) : EvolutionParam()
    class Location(val name: String) : EvolutionParam()
    class MinLevel(val value: Int) : EvolutionParam()
    class MinHappiness(val value: Int) : EvolutionParam()
    class MinBeauty(val value: Int) : EvolutionParam()
    class MinAffection(val value: Int) : EvolutionParam()
    object OverworldRain : EvolutionParam()
    class PartySpecies(val pokemon: Pokemon) : EvolutionParam()
    class PartyType(val pokemonType: PokemonType.Type) : EvolutionParam()
    sealed class PhysicalStatsRelation : EvolutionParam() {
        object Attack : PhysicalStatsRelation()
        object Equal : PhysicalStatsRelation()
        object Defense : PhysicalStatsRelation()
    }

    sealed class TimeOfDay : EvolutionParam() {
        object Day : TimeOfDay()
        object Night : TimeOfDay()
    }

    class TradeSpecies(val pokemon: Pokemon) : EvolutionParam()
    object TurnUpsideDown : EvolutionParam()
}