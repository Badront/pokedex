package com.badront.pokedex.pokemon.core.data.remote.mapper

import com.badront.pokedex.core.data.remote.model.NamedApiResourceDto
import org.junit.Before
import org.junit.Test

class PokemonDtoMapperTest {

    private lateinit var mapper: PokemonDtoMapperImpl

    @Before
    fun setup() {
        mapper = PokemonDtoMapperImpl()
    }

    @Test
    fun `pokemon image generates correctly`() {
        val id = DataFactory.randomInt()
        val name = DataFactory.randomString()
        val dto = NamedApiResourceDto(
            name = name,
            url = "https://pokeapi.co/api/v2/pokemon/$id"
        )
        val pokemon = mapper.map(dto)
        assert(pokemon.image == mapper.pokemonImageById(id))
    }

    @Test
    fun `pokemon mapping result is valid`() {
        val id = DataFactory.randomInt()
        val name = DataFactory.randomString()
        val dto = NamedApiResourceDto(
            name = name,
            url = "https://pokeapi.co/api/v2/pokemon/$id"
        )
        val url =
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png"
        val pokemon = mapper.map(dto)
        assert(pokemon.id == id)
        assert(pokemon.image == url)
        assert(pokemon.name == name)
        assert(pokemon.number == id)
    }
}