package com.badront.pokedex.pokemon.core.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = PokemonTypeEntity.TABLE_NAME,
    indices = [
        Index(PokemonTypeEntity.COLUMN_POKEMON_ID),
        Index(PokemonTypeEntity.COLUMN_TYPE)
    ],
    primaryKeys = [
        PokemonTypeEntity.COLUMN_POKEMON_ID,
        PokemonTypeEntity.COLUMN_TYPE
    ],
    foreignKeys = [
        ForeignKey(
            entity = PokemonEntity::class,
            parentColumns = [PokemonEntity.COLUMN_ID],
            childColumns = [PokemonTypeEntity.COLUMN_POKEMON_ID],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
class PokemonTypeEntity(
    @ColumnInfo(name = COLUMN_POKEMON_ID)
    val pokemonId: Int,
    @ColumnInfo(name = COLUMN_TYPE)
    val type: String,
    @ColumnInfo(name = COLUMN_ORDER)
    val order: Int
) {
    companion object {
        internal const val TABLE_NAME = "pokemon_type"
        internal const val COLUMN_POKEMON_ID = "pokemon_id"
        internal const val COLUMN_TYPE = "type"
        internal const val COLUMN_ORDER = "order"
    }
}