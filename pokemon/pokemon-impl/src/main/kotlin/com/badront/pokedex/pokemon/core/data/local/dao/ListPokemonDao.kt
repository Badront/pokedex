package com.badront.pokedex.pokemon.core.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.badront.pokedex.pokemon.core.data.local.model.PokemonEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class ListPokemonDao {
    @Query("SELECT * FROM ${PokemonEntity.TABLE_NAME}")
    abstract fun getAllAsFlow(): Flow<List<PokemonEntity>>

    @Query(QUERY_BY_ID)
    abstract suspend fun getById(id: Int): PokemonEntity?

    @Query(QUERY_BY_ID)
    abstract fun getByIdAsFlow(id: Int): Flow<PokemonEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(entities: List<PokemonEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(vararg entities: PokemonEntity)

    @Update
    abstract suspend fun update(entities: List<PokemonEntity>)

    @Delete
    abstract suspend fun delete(entities: List<PokemonEntity>)

    @Delete
    abstract suspend fun delete(vararg entities: PokemonEntity)

    @Query("DELETE FROM ${PokemonEntity.TABLE_NAME}")
    abstract suspend fun deleteAll()

    @Transaction
    open suspend fun replaceAll(entities: List<PokemonEntity>) {
        deleteAll()
        insert(entities)
    }

    private companion object {
        private const val QUERY_BY_ID = """
                SELECT * 
                FROM ${PokemonEntity.TABLE_NAME} 
                WHERE ${PokemonEntity.COLUMN_ID}=:id
            """
    }
}