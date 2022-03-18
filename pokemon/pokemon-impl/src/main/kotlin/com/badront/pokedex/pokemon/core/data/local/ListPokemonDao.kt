package com.badront.pokedex.pokemon.core.data.local

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

    @Query("SELECT * FROM ${PokemonEntity.TABLE_NAME} WHERE ${PokemonEntity.COLUMN_ID}=:id")
    abstract suspend fun getById(id: Int): PokemonEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(entities: List<PokemonEntity>)

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
}