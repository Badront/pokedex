package com.badront.pokedex.database.pokemon.dao

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.badront.pokedex.core.ext.kotlin.collections.areContentsTheSame
import com.badront.pokedex.database.DaoTest
import com.badront.pokedex.database.DataFactory
import com.badront.pokedex.database.pokemon.PokemonEntityDataFactory
import com.badront.pokedex.database.pokemon.PokemonTypeEntityDataFactory
import com.badront.pokedex.database.pokemon.model.PokemonTypeEntity
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class PokemonTypeDaoTest : DaoTest() {
    private lateinit var pokemonDao: ListPokemonDao
    private lateinit var typeDao: PokemonTypeDao

    override fun setup() {
        super.setup()
        pokemonDao = database.listPokemonDao()
        typeDao = database.pokemonTypeDao()
    }

    @Test
    fun createAndGetPokemonType() = runBlocking<Unit> {
        val pokemon = PokemonEntityDataFactory.create()
        val type = PokemonTypeEntityDataFactory.create(pokemonId = pokemon.id)
        pokemonDao.insert(pokemon)
        typeDao.insert(type)
        val result = typeDao.getTypesByPokemonId(type.pokemonId)
        assert(result.size == 1)
        assert(result.first() == type)
    }

    @Test
    fun updatePokemonTypesUpdatesCorrectly() = runBlocking<Unit> {
        val pokemon = PokemonEntityDataFactory.create()
        pokemonDao.insert(pokemon)
        val oldTypes = (0..DataFactory.randomAbsInt() % 100 + 1).map {
            PokemonTypeEntityDataFactory.create(pokemonId = pokemon.id)
        }
        typeDao.updatePokemonTypes(pokemon.id, oldTypes)
        assert(oldTypes.areContentsTheSame(typeDao.getTypesByPokemonId(pokemon.id)))

        val newTypes = oldTypes.map {
            PokemonTypeEntity(
                pokemonId = it.pokemonId,
                type = DataFactory.randomString(),
                order = DataFactory.randomInt()
            )
        }
        typeDao.updatePokemonTypes(pokemon.id, newTypes)

        val daoTypes = typeDao.getTypesByPokemonId(pokemon.id)
        assert(daoTypes.none { oldTypes.contains(it) })
        assert(newTypes.areContentsTheSame(daoTypes))
    }

    @Test
    fun deleteByPokemonIdRemovesTypes() = runBlocking<Unit> {
        val pokemon = PokemonEntityDataFactory.create()
        val types = (0..DataFactory.randomAbsInt() % 100 + 1).map {
            PokemonTypeEntityDataFactory.create(pokemonId = pokemon.id)
        }
        pokemonDao.insert(pokemon)
        typeDao.insert(types)
        val pokemon1 = PokemonEntityDataFactory.create()
        val pokemon1Types = listOf(PokemonTypeEntityDataFactory.create(pokemonId = pokemon1.id))
        pokemonDao.insert(pokemon1)
        typeDao.insert(pokemon1Types)
        assert(typeDao.getTypesByPokemonId(pokemon.id).areContentsTheSame(types))
        assert(typeDao.getTypesByPokemonId(pokemon1.id).areContentsTheSame(pokemon1Types))
        typeDao.deleteByPokemonId(pokemon.id)
        assert(typeDao.getTypesByPokemonId(pokemon.id) == emptyList<PokemonTypeEntity>())
        assert(typeDao.getTypesByPokemonId(pokemon1.id).areContentsTheSame(pokemon1Types))
    }
}