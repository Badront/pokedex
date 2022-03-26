package com.badront.pokedex.evolution.core.data.remote.mapper

import android.net.Uri
import com.badront.pokedex.core.data.remote.model.NamedApiResourceDto
import com.badront.pokedex.evolution.core.data.remote.model.EvolutionDetailsDto
import com.badront.pokedex.evolution.core.domain.model.EvolutionDetails
import com.badront.pokedex.evolution.core.domain.model.EvolutionParam
import com.badront.pokedex.item.core.data.remote.mapper.ItemImageDtoMapper
import com.badront.pokedex.item.core.domain.model.Item
import com.badront.pokedex.pokemon.core.data.remote.mapper.PokemonDtoMapper
import com.badront.pokedex.pokemon.core.data.remote.mapper.PokemonTypeTypeDtoMapper
import java.util.Locale
import javax.inject.Inject

internal class EvolutionDetailsDtoMapper @Inject constructor(
    private val triggerDtoMapper: EvolutionTriggerDtoMapper,
    private val itemImageDtoMapper: ItemImageDtoMapper,
    private val pokemonTypeDtoMapper: PokemonTypeTypeDtoMapper,
    private val pokemonDtoMapper: PokemonDtoMapper
) {
    fun map(dto: EvolutionDetailsDto): EvolutionDetails {
        return EvolutionDetails(
            params = buildList {
                if (dto.item != null) {
                    add(EvolutionParam.ItemParam(mapItem(dto.item)))
                }
                if (dto.gender != null) {
                    mapGender(dto.gender)?.let { add(it) }
                }
                if (dto.heldItem != null) {
                    add(EvolutionParam.HeldItemParam(mapItem(dto.heldItem)))
                }
                if (dto.knownMove != null) {
                    add(mapMove(dto.knownMove))
                }
                if (dto.knownMoveType != null) {
                    add(mapMoveType(dto.knownMoveType))
                }
                if (dto.location != null) {
                    add(EvolutionParam.Location(dto.location.name))
                }
                if (dto.minLevel != null) {
                    add(EvolutionParam.MinLevel(dto.minLevel))
                }
                if (dto.minHappiness != null) {
                    add(EvolutionParam.MinHappiness(dto.minHappiness))
                }
                if (dto.minBeauty != null) {
                    add(EvolutionParam.MinBeauty(dto.minBeauty))
                }
                if (dto.minAffection != null) {
                    add(EvolutionParam.MinAffection(dto.minAffection))
                }
                if (dto.needOverworldRain == true) {
                    add(EvolutionParam.OverworldRain)
                }
                if (dto.partySpecies != null) {
                    add(EvolutionParam.PartySpecies(pokemonDtoMapper.map(dto.partySpecies)))
                }
                if (dto.partyType != null) {
                    pokemonTypeDtoMapper.map(dto.partyType)?.let { add(EvolutionParam.PartyType(it)) }
                }
                if (dto.relativePhysicalStats != null) {
                    mapRelativePhysicalStats(dto.relativePhysicalStats)?.let { add(it) }
                }
                if (dto.timeOfDay != null) {
                    mapTimeOfDay(dto.timeOfDay)?.let { add(it) }
                }
                if (dto.tradeSpecies != null) {
                    add(EvolutionParam.TradeSpecies(pokemonDtoMapper.map(dto.tradeSpecies)))
                }
                if (dto.turnUpsideDown == true) {
                    add(EvolutionParam.TurnUpsideDown)
                }
            },
            trigger = triggerDtoMapper.map(dto.trigger)
        )
    }

    private fun mapTimeOfDay(timeOfDay: String): EvolutionParam.TimeOfDay? {
        return when (timeOfDay.lowercase(Locale.ENGLISH)) {
            "day" -> EvolutionParam.TimeOfDay.Day
            "night" -> EvolutionParam.TimeOfDay.Night
            else -> null
        }
    }

    private fun mapRelativePhysicalStats(stats: Int): EvolutionParam.PhysicalStatsRelation? {
        return when (stats) {
            1 -> EvolutionParam.PhysicalStatsRelation.Attack
            0 -> EvolutionParam.PhysicalStatsRelation.Equal
            -1 -> EvolutionParam.PhysicalStatsRelation.Defense
            else -> null
        }
    }

    private fun mapMoveType(dto: NamedApiResourceDto): EvolutionParam.MoveTypeParam {
        return EvolutionParam.MoveTypeParam(
            id = Uri.parse(dto.url).lastPathSegment!!.toInt(),
            name = dto.name
        )
    }

    private fun mapMove(dto: NamedApiResourceDto): EvolutionParam.MoveParam {
        return EvolutionParam.MoveParam(
            id = Uri.parse(dto.url).lastPathSegment!!.toInt(),
            name = dto.name
        )
    }

    private fun mapGender(gender: Int): EvolutionParam.Gender? {
        return when (gender) {
            1 -> EvolutionParam.Gender.Female
            2 -> EvolutionParam.Gender.Male
            else -> null
        }
    }

    private fun mapItem(dto: NamedApiResourceDto): Item {
        return Item(
            id = Uri.parse(dto.url).lastPathSegment!!.toInt(),
            name = dto.name,
            image = itemImageDtoMapper.map(dto.name)
        )
    }
}