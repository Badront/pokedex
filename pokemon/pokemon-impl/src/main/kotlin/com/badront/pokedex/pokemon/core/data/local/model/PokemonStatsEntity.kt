package com.badront.pokedex.pokemon.core.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = PokemonStatsEntity.TABLE_NAME,
    indices = [
        Index(PokemonStatsEntity.COLUMN_ID)
    ],
    primaryKeys = [
        PokemonStatsEntity.COLUMN_ID,
        PokemonStatsEntity.COLUMN_NAME
    ],
    foreignKeys = [
        ForeignKey(
            entity = PokemonEntity::class,
            parentColumns = [
                PokemonEntity.COLUMN_ID
            ],
            childColumns = [
                PokemonStatsEntity.COLUMN_ID
            ],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
class PokemonStatsEntity(
    @ColumnInfo(name = COLUMN_ID)
    val pokemonId: Int,
    @ColumnInfo(name = COLUMN_NAME)
    val name: String,
    @ColumnInfo(name = COLUMN_BASE_VALUE)
    val baseValue: Int
) {
    companion object {
        internal const val TABLE_NAME = "pokemon_stats"
        internal const val COLUMN_ID = "pokemon_id"
        internal const val COLUMN_NAME = "name"
        internal const val COLUMN_BASE_VALUE = "base_value"
    }
}