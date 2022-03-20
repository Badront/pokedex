package com.badront.pokedex.pokemon.core.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.badront.pokedex.core.util.room.SimpleListDao
import com.badront.pokedex.pokemon.core.data.local.model.PokemonStatsEntity

@Dao
abstract class PokemonStatsDao : SimpleListDao<PokemonStatsEntity>() {

    @Query("SELECT * FROM ${PokemonStatsEntity.TABLE_NAME} WHERE ${PokemonStatsEntity.COLUMN_POKEMON_ID}=:id")
    abstract suspend fun getStatsByPokemonId(id: Int): List<PokemonStatsEntity>

    @Query("DELETE FROM ${PokemonStatsEntity.TABLE_NAME} WHERE ${PokemonStatsEntity.COLUMN_POKEMON_ID}=:id")
    abstract suspend fun deleteByPokemonId(id: Int)

    @Transaction
    open suspend fun updatePokemonStats(pokemonId: Int, stats: List<PokemonStatsEntity>) {
        insertOrUpdate(stats, getStatsByPokemonId(pokemonId)) { newItem, oldItem ->
            newItem.pokemonId == oldItem.pokemonId && newItem.name == oldItem.name
        }
    }
}