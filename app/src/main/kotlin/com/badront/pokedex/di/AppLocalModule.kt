package com.badront.pokedex.di

import android.content.Context
import androidx.room.Room
import androidx.room.withTransaction
import com.badront.pokedex.core.utils.db.DbTransactionRunner
import com.badront.pokedex.data.PokedexDatabase
import com.badront.pokedex.pokemon.core.data.local.PokemonDatabase
import com.badront.pokedex.pokemon.core.data.local.migration.PokemonDetailsMigration
import com.badront.pokedex.pokemon.core.data.local.migration.PokemonDetailsUOMMigration
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppLocalModule {

    @Binds
    abstract fun bindPokemonDatabase(pokedexDatabase: PokedexDatabase): PokemonDatabase

    companion object {
        @Provides
        @Singleton
        fun providePokedexDatabase(@ApplicationContext context: Context): PokedexDatabase {
            return Room
                .databaseBuilder(context, PokedexDatabase::class.java, "pokedex-db")
                .addMigrations(PokemonDetailsMigration(1, 2))
                .addMigrations(PokemonDetailsUOMMigration(2, 3))
                .build()
        }

        @Provides
        @Singleton
        fun provideDbTransactionRunner(database: PokedexDatabase): DbTransactionRunner {
            return object : DbTransactionRunner {
                override suspend fun <T> withTransaction(block: suspend () -> T): T {
                    return database.withTransaction(block)
                }
            }
        }
    }
}