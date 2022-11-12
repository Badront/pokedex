package com.badront.pokedex.pokemon.core.data.remote.mapper

import com.badront.pokedex.pokemon.core.data.remote.model.PokemonDetailsDto
import com.badront.pokedex.pokemon.core.domain.model.PokemonType
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class DetailedPokemonDtoMapperTest {
    private lateinit var mapper: DetailedPokemonDtoMapperImpl

    @Mock
    private lateinit var pokemonDtoMapper: PokemonDtoMapper

    @Mock
    private lateinit var typeDtoMapper: PokemonTypeDtoMapper

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        mapper = DetailedPokemonDtoMapperImpl(pokemonDtoMapper, typeDtoMapper)
    }

    @Test
    fun `map calls pokemonDtoMapper with valid values`() {
        stubPokemonDtoMapper()
        stubTypeDtoMapper()
        val dto = randomDetails()
        mapper.map(dto)
        verify(pokemonDtoMapper).pokemonImageById(dto.id)
    }

    @Test
    fun `map calls typeDtoMapper with valid values`() {
        stubPokemonDtoMapper()
        stubTypeDtoMapper()
        val dto = randomDetails()
        mapper.map(dto)
        dto.types.forEach { type ->
            verify(typeDtoMapper).map(type)
        }
    }

    @Test
    fun `map details with null weight and height returns result with null weight and height`() {
        stubPokemonDtoMapper()
        stubTypeDtoMapper()
        // weight == null && height != null
        val dto1 = randomDetails(weight = null)
        val result1 = mapper.map(dto1)
        assert(result1.details != null)
        assert(result1.details?.weight == null)
        assert(result1.details?.height != null)
        // height == null && weight != null
        val dto2 = randomDetails(height = null)
        val result2 = mapper.map(dto2)
        assert(result2.details != null)
        assert(result2.details?.height == null)
        assert(result2.details?.weight != null)
        // weight == null &&  height == null
        val dto3 = randomDetails(weight = null, height = null)
        val result3 = mapper.map(dto3)
        assert(result3.details != null)
        assert(result3.details?.height == null)
        assert(result3.details?.weight == null)
    }

    @Test
    fun `map returns valid mapped result`() {
        val resultImage = DataFactory.randomString()
        val type = PokemonType(
            order = DataFactory.randomInt(),
            type = PokemonType.Type.BUG
        )
        val dto = randomDetails()
        stubPokemonDtoMapper(resultImage)
        stubTypeDtoMapper(type)
        val result = mapper.map(dto)
        assert(
            result.pokemon.id == dto.id
        )
        assert(
            result.pokemon.name == dto.name
        )
        assert(
            result.pokemon.number == dto.id
        )
        assert(
            result.pokemon.image == resultImage
        )
    }

    private fun stubPokemonDtoMapper(imageString: String = DataFactory.randomString()) {
        whenever(pokemonDtoMapper.pokemonImageById(any())).thenReturn(imageString)
    }

    private fun stubTypeDtoMapper(type: PokemonType? = PokemonType(0, PokemonType.Type.NORMAL)) {
        whenever(typeDtoMapper.map(any())).thenReturn(type)
    }

    private fun randomDetails(
        weight: Int? = DataFactory.randomInt(),
        height: Int? = DataFactory.randomInt()
    ): PokemonDetailsDto {
        return PokemonDetailsDto(
            id = DataFactory.randomInt(),
            name = DataFactory.randomString(),
            order = DataFactory.randomInt(),
            types = (0..(DataFactory.randomInt() % 10)).map {
                PokemonTypeDtoFactory.randomValid("normal")
            },
            stats = emptyList(),
            weight = weight,
            height = height
        )
    }
}