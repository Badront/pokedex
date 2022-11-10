package com.badront.pokedex.pokemon.core.data.remote.mapper

import com.badront.pokedex.core.data.remote.model.NamedApiResourceDto
import com.badront.pokedex.pokemon.core.domain.model.PokemonType
import org.junit.Test

class PokemonTypeTypeDtoMapperTest {
    private val mapper = PokemonTypeTypeDtoMapperImpl()

    @Test
    fun `type of unknown name maps correctly`() {
        val name = DataFactory.randomString()
        val dto = NamedApiResourceDto(
            name = name,
            url = DataFactory.randomString()
        )
        val result = mapper.map(dto)
        assert(result == null)
    }

    @Test
    fun `some types maps correctly`() {
        val names = mapOf(
            "normal" to PokemonType.Type.NORMAL,
            "rock" to PokemonType.Type.ROCK,
            "ghost" to PokemonType.Type.GHOST,
            "steel" to PokemonType.Type.STEEL,
            "water" to PokemonType.Type.WATER,
            "grass" to PokemonType.Type.GRASS,
            "ice" to PokemonType.Type.ICE,
            "bug" to PokemonType.Type.BUG
        )
        names.forEach { (name, type) ->
            assert(mapper.map(NamedApiResourceDto(name, "")) == type)
        }
    }
}