package com.badront.pokedex.pokemon.core.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = ListPokemonEntity.TABLE_NAME,
    primaryKeys = [ListPokemonEntity.COLUMN_ID]
)
class ListPokemonEntity(
    @ColumnInfo(name = COLUMN_ID)
    val id: Int,
    @ColumnInfo(name = COLUMN_NAME)
    val name: String,
    @ColumnInfo(name = COLUMN_IMAGE)
    val image: String,
    @ColumnInfo(name = COLUMN_NUMBER)
    val number: Int
) {
    companion object {
        internal const val TABLE_NAME = "list_pokemon"
        internal const val COLUMN_ID = "id"
        internal const val COLUMN_NAME = "name"
        internal const val COLUMN_IMAGE = "image"
        internal const val COLUMN_NUMBER = "number"
    }
}