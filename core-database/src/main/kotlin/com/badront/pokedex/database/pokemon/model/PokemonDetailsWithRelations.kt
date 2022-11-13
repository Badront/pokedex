package com.badront.pokedex.database.pokemon.model

import androidx.room.Embedded
import androidx.room.Relation

class PokemonDetailsWithRelations(
    @Embedded
    val details: PokemonDetailsEntity,
    @Relation(
        parentColumn = PokemonDetailsEntity.COLUMN_ID,
        entity = PokemonTypeEntity::class,
        entityColumn = PokemonTypeEntity.COLUMN_POKEMON_ID
    )
    val types: List<PokemonTypeEntity>,
    @Relation(
        parentColumn = PokemonDetailsEntity.COLUMN_ID,
        entity = PokemonStatsEntity::class,
        entityColumn = PokemonStatsEntity.COLUMN_POKEMON_ID
    )
    val stats: List<PokemonStatsEntity>
)