package com.badront.pokedex.database.pokemon.dao

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.badront.pokedex.core.ext.kotlin.collections.areContentsTheSame
import com.badront.pokedex.database.DaoTest
import com.badront.pokedex.database.DataFactory
import com.badront.pokedex.database.pokemon.PokemonEntityDataFactory
import com.badront.pokedex.database.pokemon.model.PokemonEntity
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class ListPokemonDaoTest : DaoTest() {
    private lateinit var pokemonDao: ListPokemonDao

    @Before
    override fun setup() {
        super.setup()
        pokemonDao = database.listPokemonDao()
    }

    @Test
    fun getAllAsFlowReturnsEmptyListIfThereIsNoItems() = runBlocking<Unit> {
        val result = pokemonDao.getAllAsFlow().firstOrNull()
        assert(result == emptyList<PokemonEntity>())
    }

    @Test
    fun getAllAsFlowReturnsInsertedElements() = runBlocking<Unit> {
        val pokemons =
            (0..DataFactory.randomAbsInt() % 1000 + 1)
                .map { PokemonEntityDataFactory.create() }
                .distinctBy { it.id }
        pokemonDao.insert(pokemons)
        val result = pokemonDao.getAllAsFlow().firstOrNull()
        assert(pokemons.areContentsTheSame(result!!))
    }

    @Test
    fun getByIdReturnsNullIfNoItemsFound() = runBlocking<Unit> {
        val pokemons =
            (0..DataFactory.randomAbsInt() % 1000 + 1)
                .map { PokemonEntityDataFactory.create() }
                .distinctBy { it.id }
        val notInList = pokemons.first()
        val inserted = pokemons.drop(1)
        pokemonDao.insert(inserted)
        assert(pokemonDao.getById(notInList.id) == null)
    }

    @Test
    fun getByIdReturnsElementIfItemFound() = runBlocking<Unit> {
        val pokemons =
            (0..DataFactory.randomAbsInt() % 1000 + 1)
                .map { PokemonEntityDataFactory.create() }
                .distinctBy { it.id }
        val inserted = pokemons.first()
        pokemonDao.insert(pokemons)
        assert(pokemonDao.getById(inserted.id) == inserted)
    }
}