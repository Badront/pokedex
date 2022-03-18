package com.badront.pokedex.pokemon.core.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = PokemonDetailsEntity.TABLE_NAME,
    indices = [
        Index(
            PokemonDetailsEntity.COLUMN_ID
        )],
    primaryKeys = [PokemonDetailsEntity.COLUMN_ID],
    foreignKeys = [
        ForeignKey(
            entity = PokemonEntity::class,
            parentColumns = [PokemonEntity.COLUMN_ID],
            childColumns = [PokemonDetailsEntity.COLUMN_ID],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
class PokemonDetailsEntity(
    @ColumnInfo(name = COLUMN_ID)
    val id: Int,
    @ColumnInfo(name = COLUMN_WEIGHT)
    val weight: Int?,
    @ColumnInfo(name = COLUMN_HEIGHT)
    val height: Int?
) {
    companion object {
        internal const val TABLE_NAME = "pokemon_details"
        internal const val COLUMN_ID = "id"
        internal const val COLUMN_WEIGHT = "weight"
        internal const val COLUMN_HEIGHT = "height"
    }
}