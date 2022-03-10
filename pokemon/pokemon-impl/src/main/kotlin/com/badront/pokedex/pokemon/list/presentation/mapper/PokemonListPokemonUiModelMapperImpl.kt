package com.badront.pokedex.pokemon.list.presentation.mapper

import com.badront.pokedex.pokemon.core.domain.model.ListPokemon
import com.badront.pokedex.pokemon.list.presentation.model.PokemonListUiModel
import javax.inject.Inject

internal class PokemonListPokemonUiModelMapperImpl @Inject constructor() : PokemonListPokemonUiModelMapper {
    override fun map(model: ListPokemon): PokemonListUiModel.Pokemon {
        return PokemonListUiModel.Pokemon(
            id = model.id,
            number = model.number.toString().padStart(4, '0'),
            name = model.name,
            image = model.image
        )
    }
}