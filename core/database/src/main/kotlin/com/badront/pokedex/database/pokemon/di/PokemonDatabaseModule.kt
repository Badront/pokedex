package com.badront.pokedex.database.pokemon.di

import com.badront.pokedex.database.PokedexDatabase
import com.badront.pokedex.database.pokemon.dao.ListPokemonDao
import com.badront.pokedex.database.pokemon.dao.PokemonDetailsDao
import com.badront.pokedex.database.pokemon.dao.PokemonStatsDao
import com.badront.pokedex.database.pokemon.dao.PokemonTypeDao
import dagger.Module
import dagger.Provides
import dagger.hilt.migration.DisableInstallInCheck

@Module
@DisableInstallInCheck
internal abstract class PokemonDatabaseModule {
    companion object {
        @Provides
        fun provideListPokemonDao(pokemonDatabase: PokedexDatabase): ListPokemonDao {
            return pokemonDatabase.listPokemonDao()
        }

        @Provides
        fun providePokemonDetailsDao(pokemonDatabase: PokedexDatabase): PokemonDetailsDao {
            return pokemonDatabase.pokemonDetailsDao()
        }

        @Provides
        fun providePokemonTypeDao(pokemonDatabase: PokedexDatabase): PokemonTypeDao {
            return pokemonDatabase.pokemonTypeDao()
        }

        @Provides
        fun providePokemonStatsDao(pokemonDatabase: PokedexDatabase): PokemonStatsDao {
            return pokemonDatabase.pokemonStatsDao()
        }

    }
}