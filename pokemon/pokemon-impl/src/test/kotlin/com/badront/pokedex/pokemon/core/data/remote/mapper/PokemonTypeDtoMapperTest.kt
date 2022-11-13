package com.badront.pokedex.pokemon.core.data.remote.mapper

import com.badront.pokedex.core.data.remote.model.NamedApiResourceDto
import com.badront.pokedex.pokemon.DataFactory
import com.badront.pokedex.pokemon.core.data.remote.model.PokemonTypeDto
import com.badront.pokedex.pokemon.core.domain.model.PokemonType
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class PokemonTypeDtoMapperTest {

    @Mock
    private lateinit var typeMapper: PokemonTypeTypeDtoMapper

    private lateinit var mapper: PokemonTypeDtoMapperImpl

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        mapper = PokemonTypeDtoMapperImpl(typeMapper)
    }

    private fun stubTypeMapperCalls(dto: NamedApiResourceDto, result: PokemonType.Type?) {
        whenever(typeMapper.map(dto)).thenReturn(result)
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
        mapper.map(dto)
        verify(typeMapper).map(dto.type)
    }

    @Test
    fun `map of dto returns null if type not found`() {
        val dto = PokemonTypeDtoFactory.randomInvalid()
        stubTypeMapperCalls(dto.type, null)
        val result = mapper.map(dto)
        assert(result == null)
    }

    @Test
    fun `map of dto returns valid result if type is found`() {
        val dto = PokemonTypeDtoFactory.randomValid("normal")
        val resultType = PokemonType.Type.NORMAL
        stubTypeMapperCalls(dto.type, resultType)
        val result = mapper.map(dto)
        assert(result?.type == resultType)
    }
}