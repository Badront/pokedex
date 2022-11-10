package com.badront.pokedex.pokemon.core.data.remote.mapper

import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class DetailedPokemonDtoMapperTest {
    private lateinit var mapper: DetailedPokemonDtoMapper

    @Mock
    private lateinit var pokemonDtoMapper: PokemonDtoMapper

    @Mock
    private lateinit var typeDtoMapper: PokemonTypeDtoMapper

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        mapper = DetailedPokemonDtoMapper(pokemonDtoMapper, typeDtoMapper)
    }

    @Test
    fun `map calls pokemonDtoMapper with valid values`() = runBlocking<Unit> {

    }
}