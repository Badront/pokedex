package com.badront.pokedex.pokemon.core.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.badront.pokedex.core.data.local.dao.SimpleListDao
import com.badront.pokedex.pokemon.core.data.local.model.PokemonStatsEntity

@Dao
abstract class PokemonStatsDao : SimpleListDao<PokemonStatsEntity>() {

    @Query("SELECT * FROM ${PokemonStatsEntity.TABLE_NAME} where ${PokemonStatsEntity.COLUMN_POKEMON_ID}=:id")
    abstract suspend fun getStatsByPokemonId(id: Int): List<PokemonStatsEntity>

    @Transaction
    open suspend fun updatePokemonDetails(pokemonId: Int, stats: List<PokemonStatsEntity>) {
        insertOrUpdate(stats, getStatsByPokemonId(pokemonId)) { newItem, oldItem ->
            newItem.pokemonId == oldItem.pokemonId && newItem.name == oldItem.name
        }
    }
}