package com.badront.pokedex.database.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.withTransaction
import com.badront.pokedex.database.PokedexDatabase
import com.badront.pokedex.database.pokemon.di.PokemonDatabaseModule
import com.badront.pokedex.database.pokemon.migration.PokemonDetailsMigration
import com.badront.pokedex.database.pokemon.migration.PokemonDetailsUOMMigration
import com.badront.pokedex.database.utils.DbTransactionRunner
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module(includes = [PokemonDatabaseModule::class])
@InstallIn(SingletonComponent::class)
abstract class AppLocalModule {

    @Binds
    abstract fun bindPokemonDatabase(pokedexDatabase: PokedexDatabase): RoomDatabase

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