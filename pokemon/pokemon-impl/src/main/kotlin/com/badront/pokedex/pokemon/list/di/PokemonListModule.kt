package com.badront.pokedex.pokemon.list.di

import com.badront.pokedex.pokemon.list.presentation.mapper.PokemonListPokemonUiModelMapper
import com.badront.pokedex.pokemon.list.presentation.mapper.PokemonListPokemonUiModelMapperImpl
import com.badront.pokedex.pokemon.list.presentation.mapper.PokemonListUiModelMapper
import com.badront.pokedex.pokemon.list.presentation.mapper.PokemonListUiModelMapperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.migration.DisableInstallInCheck

@Module
@DisableInstallInCheck
internal abstract class PokemonListModule {
    @Binds
    abstract fun provideListUiMapper(mapper: PokemonListUiModelMapperImpl): PokemonListUiModelMapper

    @Binds
    abstract fun providePokemonUiMapper(mapper: PokemonListPokemonUiModelMapperImpl): PokemonListPokemonUiModelMapper
}