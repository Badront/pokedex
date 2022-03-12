package com.badront.pokedex.pokemon.core.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.badront.pokedex.pokemon.core.data.local.model.ListPokemonEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class ListPokemonDao {
    @Query("SELECT * FROM ${ListPokemonEntity.TABLE_NAME}")
    abstract fun getAllAsFlow(): Flow<List<ListPokemonEntity>>

    @Query("SELECT * FROM ${ListPokemonEntity.TABLE_NAME} WHERE ${ListPokemonEntity.COLUMN_ID}=:id")
    abstract suspend fun getById(id: Int): ListPokemonEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(entities: List<ListPokemonEntity>)

    @Update
    abstract suspend fun update(entities: List<ListPokemonEntity>)

    @Delete
    abstract suspend fun delete(entities: List<ListPokemonEntity>)

    @Delete
    abstract suspend fun delete(vararg entities: ListPokemonEntity)

    @Query("DELETE FROM ${ListPokemonEntity.TABLE_NAME}")
    abstract suspend fun deleteAll()

    @Transaction
    open suspend fun replaceAll(entities: List<ListPokemonEntity>) {
        deleteAll()
        insert(entities)
    }
}