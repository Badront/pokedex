package com.badront.pokedex.pokemon.core.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.badront.pokedex.pokemon.core.data.local.model.PokemonDetailsEntity
import com.badront.pokedex.pokemon.core.data.local.model.PokemonDetailsWithRelations
import kotlinx.coroutines.flow.Flow

@Dao
abstract class PokemonDetailsDao {
    @Transaction
    @Query(QUERY_BY_ID)
    abstract suspend fun getPokemonDetailsById(id: Int): PokemonDetailsWithRelations?

    @Transaction
    @Query(QUERY_BY_ID)
    abstract fun getPokemonDetailsByIdAsFlow(id: Int): Flow<PokemonDetailsWithRelations?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertDetails(details: PokemonDetailsEntity)

    @Delete
    abstract suspend fun deleteDetails(details: PokemonDetailsEntity)

    private companion object {
        private const val QUERY_BY_ID = """
            SELECT * FROM ${PokemonDetailsEntity.TABLE_NAME}
            WHERE ${PokemonDetailsEntity.COLUMN_ID}=:id
        """
    }
}