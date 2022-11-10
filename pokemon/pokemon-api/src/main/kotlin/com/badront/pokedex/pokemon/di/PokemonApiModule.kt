package com.badront.pokedex.pokemon.di

import com.badront.pokedex.pokemon.core.data.remote.mapper.PokemonDtoMapper
import com.badront.pokedex.pokemon.core.data.remote.mapper.PokemonDtoMapperImpl
import com.badront.pokedex.pokemon.core.data.remote.mapper.PokemonTypeTypeDtoMapper
import com.badront.pokedex.pokemon.core.data.remote.mapper.PokemonTypeTypeDtoMapperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class PokemonApiModule {
    @Binds
    abstract fun bindPokemonDtoMapper(mapperImpl: PokemonDtoMapperImpl): PokemonDtoMapper

    @Binds
    abstract fun bindPokemonTypeTypeDtoMapper(mapperImpl: PokemonTypeTypeDtoMapperImpl): PokemonTypeTypeDtoMapper
}