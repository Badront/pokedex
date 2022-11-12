package com.badront.pokedex.pokemon.core.data.local.mapper

import com.badront.pokedex.pokemon.core.data.local.model.PokemonEntity
import com.badront.pokedex.pokemon.core.data.remote.mapper.DataFactory
import com.badront.pokedex.pokemon.core.domain.model.Pokemon
import org.junit.Test

class ListPokemonEntityMapperTest {
    private val mapper = ListPokemonEntityMapper()

    @Test
    fun `map entity to model is valid`() {
        val entity = PokemonEntity(
            id = DataFactory.randomInt(),
            name = DataFactory.randomString(),
            image = DataFactory.randomString(),
            number = DataFactory.randomInt()
        )
        val model = mapper.map(entity)
        assert(
            model.id == entity.id
        )
        assert(
            model.name == entity.name
        )
        assert(
            model.image == entity.image
        )
        assert(
            model.number == entity.number
        )
    }

    @Test
    fun `map model to entity is valid`() {
        val model = Pokemon(
            id = DataFactory.randomInt(),
            name = DataFactory.randomString(),
            image = DataFactory.randomString(),
            number = DataFactory.randomInt()
        )
        val entity = mapper.map(model)
        assert(
            model.id == entity.id
        )
        assert(
            model.name == entity.name
        )
        assert(
            model.image == entity.image
        )
        assert(
            model.number == entity.number
        )
    }
}