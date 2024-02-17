package com.badront.pokedex.database.pokemon.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.badront.pokedex.database.pokemon.model.PokemonTypeEntity
import com.badront.pokedex.database.utils.SimpleListDao

@Dao
abstract class PokemonTypeDao : SimpleListDao<PokemonTypeEntity>() {

    @Query("SELECT * FROM ${PokemonTypeEntity.TABLE_NAME} WHERE ${PokemonTypeEntity.COLUMN_POKEMON_ID}=:id")
    abstract suspend fun getTypesByPokemonId(id: Int): List<PokemonTypeEntity>

    @Query("DELETE FROM ${PokemonTypeEntity.TABLE_NAME} WHERE ${PokemonTypeEntity.COLUMN_POKEMON_ID}=:id")
    abstract suspend fun deleteByPokemonId(id: Int)

    @Transaction
    open suspend fun updatePokemonTypes(pokemonId: Int, types: List<PokemonTypeEntity>) {
        insertOrUpdate(types, getTypesByPokemonId(pokemonId)) { newItem, oldItem ->
            newItem.pokemonId == oldItem.pokemonId && newItem.type == oldItem.type
        }
    }
}