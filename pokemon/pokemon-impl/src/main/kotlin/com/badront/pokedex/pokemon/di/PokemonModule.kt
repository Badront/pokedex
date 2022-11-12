package com.badront.pokedex.pokemon.di

import com.badront.pokedex.pokemon.PokemonDestinationsProvider
import com.badront.pokedex.pokemon.PokemonDestinationsProviderImpl
import com.badront.pokedex.pokemon.core.data.PokemonListRepositoryImpl
import com.badront.pokedex.pokemon.core.data.PokemonRepositoryImpl
import com.badront.pokedex.pokemon.core.data.local.PokemonDatabase
import com.badront.pokedex.pokemon.core.data.local.dao.ListPokemonDao
import com.badront.pokedex.pokemon.core.data.local.dao.PokemonDetailsDao
import com.badront.pokedex.pokemon.core.data.local.dao.PokemonStatsDao
import com.badront.pokedex.pokemon.core.data.local.dao.PokemonTypeDao
import com.badront.pokedex.pokemon.core.data.remote.PokemonApi
import com.badront.pokedex.pokemon.core.data.remote.mapper.DetailedPokemonDtoMapper
import com.badront.pokedex.pokemon.core.data.remote.mapper.DetailedPokemonDtoMapperImpl
import com.badront.pokedex.pokemon.core.data.remote.mapper.PokemonTypeDtoMapper
import com.badront.pokedex.pokemon.core.data.remote.mapper.PokemonTypeDtoMapperImpl
import com.badront.pokedex.pokemon.core.domain.repository.PokemonListRepository
import com.badront.pokedex.pokemon.core.domain.repository.PokemonRepository
import com.badront.pokedex.pokemon.details.di.PokemonDetailsModule
import com.badront.pokedex.pokemon.list.di.PokemonListModule
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module(includes = [PokemonListModule::class, PokemonDetailsModule::class])
@InstallIn(SingletonComponent::class)
internal abstract class PokemonModule {

    @Binds
    abstract fun bindPokemonTypeDtoMapper(mapper: PokemonTypeDtoMapperImpl): PokemonTypeDtoMapper

    @Binds
    abstract fun bindDetailedPokemonDtoMapper(mapper: DetailedPokemonDtoMapperImpl): DetailedPokemonDtoMapper

    @Binds
    abstract fun bindPokemonListRepository(repository: PokemonListRepositoryImpl): PokemonListRepository

    @Binds
    abstract fun bindPokemonRepository(repository: PokemonRepositoryImpl): PokemonRepository

    @Binds
    abstract fun bindPokemonDestinationProvider(provider: PokemonDestinationsProviderImpl): PokemonDestinationsProvider

    companion object {
        @Provides
        fun providePokemonApi(retrofit: Retrofit): PokemonApi {
            return retrofit.create(PokemonApi::class.java)
        }

        @Provides
        fun provideListPokemonDao(pokemonDatabase: PokemonDatabase): ListPokemonDao {
            return pokemonDatabase.listPokemonDao()
        }

        @Provides
        fun providePokemonDetailsDao(pokemonDatabase: PokemonDatabase): PokemonDetailsDao {
            return pokemonDatabase.pokemonDetailsDao()
        }

        @Provides
        fun providePokemonTypeDao(pokemonDatabase: PokemonDatabase): PokemonTypeDao {
            return pokemonDatabase.pokemonTypeDao()
        }

        @Provides
        fun providePokemonStatsDao(pokemonDatabase: PokemonDatabase): PokemonStatsDao {
            return pokemonDatabase.pokemonStatsDao()
        }
    }
}