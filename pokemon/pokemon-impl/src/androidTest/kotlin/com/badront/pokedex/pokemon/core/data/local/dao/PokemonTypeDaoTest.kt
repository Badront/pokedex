package com.badront.pokedex.pokemon.core.data.local.dao

import PokemonTypeEntity
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.badront.pokedex.pokemon.DataFactory
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
@SmallTest
class PokemonTypeDaoTest {
    private lateinit var database: MockPokemonDatabase
    private lateinit var typeDao: com.badreont.pokedex.database.pokemon.dao.PokemonTypeDao

    @Before
    fun setup() {
        // todo вынести бд в отдельный core-модуль
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MockPokemonDatabase::class.java
        ).allowMainThreadQueries().build()
        typeDao = database.pokemonTypeDao()
    }

    @After
    @Throws(IOException::class)
    fun close() {
        database.close()
    }

    @Test
    @Throws(Exception::class)
    fun createAndGetPokemonType() = runBlocking<Unit> {
        val type = PokemonTypeEntity(
            pokemonId = DataFactory.randomInt(),
            type = DataFactory.randomString(),
            order = DataFactory.randomInt()
        )
        typeDao.insert(type)
        val result = typeDao.getTypesByPokemonId(type.pokemonId)
        assert(result.size == 1)
        assert(result.first() == type)
    }
}