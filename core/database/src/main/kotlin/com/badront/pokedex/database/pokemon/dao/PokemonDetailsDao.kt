package com.badront.pokedex.database.pokemon.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.badront.pokedex.database.pokemon.model.PokemonDetailsEntity
import com.badront.pokedex.database.pokemon.model.PokemonDetailsWithRelations
import com.badront.pokedex.database.utils.SimpleListDao
import kotlinx.coroutines.flow.Flow

@Dao
abstract class PokemonDetailsDao : SimpleListDao<PokemonDetailsEntity>() {
    @Query(QUERY_BY_ID)
    abstract suspend fun getPokemonDetailsById(id: Int): PokemonDetailsEntity?

    @Transaction
    @Query(QUERY_BY_ID)
    abstract suspend fun getPokemonDetailsRelationsById(id: Int): PokemonDetailsWithRelations?

    @Transaction
    @Query(QUERY_BY_ID)
    abstract fun getPokemonDetailsRelationsByIdAsFlow(id: Int): Flow<PokemonDetailsWithRelations?>

    @Query(DELETE_BY_ID)
    abstract suspend fun deleteById(id: Int)

    @Transaction
    open suspend fun insertOrUpdate(details: PokemonDetailsEntity) {
        if (getPokemonDetailsById(details.id) != null) {
            update(details)
        } else {
            insert(details)
        }
    }

    private companion object {
        private const val QUERY_BY_ID = """
            SELECT * FROM ${PokemonDetailsEntity.TABLE_NAME}
            WHERE ${PokemonDetailsEntity.COLUMN_ID}=:id
        """
        private const val DELETE_BY_ID = """
            DELETE FROM ${PokemonDetailsEntity.TABLE_NAME}
            WHERE ${PokemonDetailsEntity.COLUMN_ID}=:id
        """
    }
}