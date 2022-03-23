package com.badront.pokedex.pokemon.di

import com.badront.pokedex.pokemon.core.data.PokemonSpeciesRepositoryImpl
import com.badront.pokedex.pokemon.core.data.remote.PokemonSpeciesApi
import com.badront.pokedex.pokemon.core.domain.PokemonSpeciesRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
internal abstract class PokemonSpeciesModule {

    @Binds
    abstract fun bindPokemonSpeciesRepository(repository: PokemonSpeciesRepositoryImpl): PokemonSpeciesRepository

    companion object {
        @Provides
        fun providePokemonSpeciesApi(retrofit: Retrofit): PokemonSpeciesApi {
            return retrofit.create(PokemonSpeciesApi::class.java)
        }
    }
}