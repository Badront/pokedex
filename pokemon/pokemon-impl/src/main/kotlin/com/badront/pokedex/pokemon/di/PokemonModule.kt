package com.badront.pokedex.pokemon.di

import com.badront.pokedex.pokemon.core.data.PokemonListRepositoryImpl
import com.badront.pokedex.pokemon.core.data.remote.PokemonApi
import com.badront.pokedex.pokemon.core.domain.PokemonListRepository
import com.badront.pokedex.pokemon.list.di.PokemonListModule
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module(includes = [PokemonListModule::class])
@InstallIn(SingletonComponent::class)
abstract class PokemonModule {

    @Binds
    abstract fun bindPokemonListRepository(repository: PokemonListRepositoryImpl): PokemonListRepository

    companion object {
        @Provides
        fun providePokemonApi(retrofit: Retrofit): PokemonApi {
            return retrofit.create(PokemonApi::class.java)
        }
    }
}