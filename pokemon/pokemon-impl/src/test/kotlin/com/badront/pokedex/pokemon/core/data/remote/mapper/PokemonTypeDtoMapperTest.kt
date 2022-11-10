package com.badront.pokedex.pokemon.core.data.remote.mapper

import com.badront.pokedex.core.data.remote.model.NamedApiResourceDto
import com.badront.pokedex.pokemon.core.data.remote.model.PokemonTypeDto
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class PokemonTypeDtoMapperTest {

    @Mock
    private lateinit var typeMapper: PokemonTypeTypeDtoMapper

    private lateinit var mapper: PokemonTypeDtoMapper

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        mapper = PokemonTypeDtoMapper(typeMapper)
    }

    private fun stubTypeMapperCalls() {
        whenever(typeMapper.map(any())).thenReturn(any())
    }

    @Test
    fun `map of dto calls type map correctly`() {
        val dto = PokemonTypeDto(
            slot = DataFactory.randomInt(),
            type = NamedApiResourceDto(
                name = DataFactory.randomString(),
                url = DataFactory.randomString()
            )
        )
        val result = mapper.map(dto)
        verify(typeMapper).map(dto.type)
    }
}