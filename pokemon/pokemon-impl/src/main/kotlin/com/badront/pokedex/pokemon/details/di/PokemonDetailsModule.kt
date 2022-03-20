package com.badront.pokedex.pokemon.details.di

import com.badront.pokedex.pokemon.details.presentation.mapper.DetailedPokemonHeaderUiModelMapper
import com.badront.pokedex.pokemon.details.presentation.mapper.DetailedPokemonHeaderUiModelMapperImpl
import com.badront.pokedex.pokemon.details.presentation.mapper.DetailedPokemonUiModelMapper
import com.badront.pokedex.pokemon.details.presentation.mapper.DetailedPokemonUiModelMapperImpl
import com.badront.pokedex.pokemon.details.presentation.mapper.PokemonDetailsTypeUiMapper
import com.badront.pokedex.pokemon.details.presentation.mapper.PokemonDetailsTypeUiMapperImpl
import com.badront.pokedex.pokemon.details.presentation.mapper.PokemonDetailsUiModelMapper
import com.badront.pokedex.pokemon.details.presentation.mapper.PokemonDetailsUiModelMapperImpl
import com.badront.pokedex.pokemon.details.presentation.mapper.PokemonTypeColorMapper
import com.badront.pokedex.pokemon.details.presentation.mapper.PokemonTypeColorMapperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.migration.DisableInstallInCheck

@Module
@DisableInstallInCheck
internal abstract class PokemonDetailsModule {

    @Binds
    abstract fun bindDetailedPokemonUiModelMapper(
        mapper: DetailedPokemonUiModelMapperImpl
    ): DetailedPokemonUiModelMapper

    @Binds
    abstract fun bindDetailedPokemonHeaderUiModelMapper(
        mapper: DetailedPokemonHeaderUiModelMapperImpl
    ): DetailedPokemonHeaderUiModelMapper

    @Binds
    abstract fun bindPokemonDetailsUiModelMapper(
        mapper: PokemonDetailsUiModelMapperImpl
    ): PokemonDetailsUiModelMapper

    @Binds
    abstract fun bindPokemonDetailsTypeUiMapper(
        mapper: PokemonDetailsTypeUiMapperImpl
    ): PokemonDetailsTypeUiMapper

    @Binds
    abstract fun bindPokemonTypeColorMapper(
        mapper: PokemonTypeColorMapperImpl
    ): PokemonTypeColorMapper
}