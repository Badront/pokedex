package com.badront.pokedex.di

import android.content.Context
import androidx.room.Room
import com.badront.pokedex.data.PokedexDatabase
import com.badront.pokedex.pokemon.core.data.local.PokemonDatabase
import com.badront.pokedex.pokemon.core.data.local.migration.PokemonDetailsMigration
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
                .build()
        }
    }
}