package com.badront.pokedex.pokemon.core.data

import com.badront.pokedex.core.model.Either
import com.badront.pokedex.core.model.Page
import com.badront.pokedex.core.model.PageInfo
import com.badront.pokedex.database.pokemon.dao.ListPokemonDao
import com.badront.pokedex.database.pokemon.model.PokemonEntity
import com.badront.pokedex.pokemon.DataFactory
import com.badront.pokedex.pokemon.core.data.local.mapper.ListPokemonEntityMapper
import com.badront.pokedex.pokemon.core.data.remote.PokemonApi
import com.badront.pokedex.pokemon.core.data.remote.mapper.PokemonDtoMapper
import com.badront.pokedex.pokemon.core.data.remote.model.ListPokemonResultDto
import com.badront.pokedex.pokemon.core.data.remote.model.PokemonDto
import com.badront.pokedex.pokemon.core.domain.model.Pokemon
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import retrofit2.HttpException
import retrofit2.Response

class PokemonListRepositoryTest {
    private lateinit var repository: PokemonListRepositoryImpl

    @Mock
    private lateinit var pokemonApi: PokemonApi

    @Mock
    private lateinit var listPokemonDao: ListPokemonDao

    @Mock
    private lateinit var pokemonDtoMapper: PokemonDtoMapper

    private lateinit var listPokemonEntityMapper: ListPokemonEntityMapper

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        listPokemonEntityMapper = ListPokemonEntityMapper()
        repository = PokemonListRepositoryImpl(pokemonApi, listPokemonDao, pokemonDtoMapper, listPokemonEntityMapper)
    }

    @Test
    fun `load pokemon list calls valid api method`() = runBlocking<Unit> {
        stubPokemonApiSuccess()
        val pageInfo = getPageInfo()
        repository.loadPokemonList(pageInfo)
        verify(pokemonApi).getPokemonsPage(pageInfo.offset, pageInfo.limit)
    }

    @Test
    fun `if load pokemon list throws valid exception method returns Failure`() = runBlocking<Unit> {
        stubPokemonApiThrows()
        val pageInfo = getPageInfo()
        val result = repository.loadPokemonList(pageInfo)
        assert(result is Either.Failure)
    }

    @Test(expected = CancellationException::class)
    fun `if load pokemon list throws CancellationException it goes up`() = runBlocking<Unit> {
        stubPokemonApiThrows(exception = CancellationException())
        val pageInfo = getPageInfo()
        repository.loadPokemonList(pageInfo)
    }

    @Test
    fun `if load pokemon lists returns valid items method returns Success`() = runBlocking<Unit> {
        stubPokemonApiSuccess(pokemonSuccessApiResult())
        val pageInfo = getPageInfo()
        val result = repository.loadPokemonList(pageInfo)
        assert(result is Either.Success)
    }

    @Test
    fun `if load pokemon lists returns valid items pokemonDtoMapper called`() = runBlocking<Unit> {
        val dto = pokemonSuccessApiResult()
        stubPokemonApiSuccess(dto)
        val pageInfo = getPageInfo()
        repository.loadPokemonList(pageInfo)
        dto.items.forEach { pokemon ->
            verify(pokemonDtoMapper).map(pokemon)
        }
    }

    @Test
    fun `if load pokemon lists returns valid items result is Page`() = runBlocking<Unit> {
        val dto = pokemonSuccessApiResult()
        stubPokemonApiSuccess(dto)
        val pageInfo = getPageInfo()
        val result = repository.loadPokemonList(pageInfo)
        assert(result.getOrNull() is Page)
        assert(result.getOrNull()?.total == dto.totalCount)
        assert(result.getOrNull()?.items?.count() == dto.items.count())
    }

    @Test
    fun `get pokemons as flow calls dao for all values`() = runBlocking<Unit> {
        stubPokemonDaoGetAllAsFlow(getPokemonEntities())
        repository.getPokemonsAsFlow().firstOrNull()
        verify(listPokemonDao).getAllAsFlow()
    }

    @Test
    fun `get pokemons as flow returns mapped values from dao`() = runBlocking<Unit> {
        val entities = getPokemonEntities()
        stubPokemonDaoGetAllAsFlow(entities)
        val result = repository.getPokemonsAsFlow().firstOrNull()
        assert(result != null)
        result?.mapIndexed { index, pokemon ->
            val entity = entities[index]
            assert(pokemon == listPokemonEntityMapper.map(entity))
        }
    }

    @Test
    fun `get pokemons as flow returns emptyList if no entities in db`() = runBlocking<Unit> {
        stubPokemonDaoGetAllAsFlow(emptyList())
        val result = repository.getPokemonsAsFlow().firstOrNull()
        assert(result == emptyList<Pokemon>())
    }

    @Test
    fun `get pokemon by id as flow calls dao`() = runBlocking<Unit> {
        stubPokemonDaoGetByIdAsFlow()
        val id = DataFactory.randomInt()
        repository.getPokemonByIdAsFlow(id).firstOrNull()
        verify(listPokemonDao).getByIdAsFlow(id)
    }

    @Test
    fun `get pokemon by id as flow returns null if no entity in db`() = runBlocking<Unit> {
        stubPokemonDaoGetByIdAsFlow(null)
        val id = DataFactory.randomInt()
        val result = repository.getPokemonByIdAsFlow(id).firstOrNull()
        assert(result == null)
    }

    @Test
    fun `get pokemon by id as flow returns mapped value from dao`() = runBlocking<Unit> {
        val pokemon = getPokemonEntity()
        stubPokemonDaoGetByIdAsFlow(pokemon)
        val id = DataFactory.randomInt()
        val result = repository.getPokemonByIdAsFlow(id).firstOrNull()
        verify(result != null)
        assert(result == listPokemonEntityMapper.map(pokemon))
    }

    private fun getPageInfo(): PageInfo {
        return PageInfo(
            limit = DataFactory.randomInt(),
            offset = DataFactory.randomInt()
        )
    }

    private fun pokemonSuccessApiResult(): ListPokemonResultDto<PokemonDto> {
        val dtoItems = (0..DataFactory.randomAbsInt() % 1000 + 1).map {
            PokemonDto(
                name = DataFactory.randomString(),
                url = DataFactory.randomString()
            )
        }
        return ListPokemonResultDto(
            totalCount = dtoItems.size,
            nextUrl = null,
            prevUrl = null,
            items = dtoItems
        )
    }

    private suspend fun stubPokemonApiSuccess(
        result: ListPokemonResultDto<PokemonDto> = pokemonSuccessApiResult()
    ) {
        whenever(pokemonApi.getPokemonsPage(any(), any())).thenReturn(result)
    }

    private suspend fun stubPokemonApiThrows(
        exception: Throwable = HttpException(
            Response.error<ListPokemonResultDto<PokemonDto>>(
                400,
                "error".toResponseBody()
            )
        )
    ) {
        whenever(pokemonApi.getPokemonsPage(any(), any())).thenThrow(exception)
    }

    private fun getPokemonEntities(): List<PokemonEntity> {
        return (0..DataFactory.randomAbsInt() % 1000 + 1).map {
            getPokemonEntity()
        }
    }

    private fun getPokemonEntity(): PokemonEntity {
        return PokemonEntity(
            id = DataFactory.randomInt(),
            name = DataFactory.randomString(),
            image = DataFactory.randomString(),
            number = DataFactory.randomInt()
        )
    }

    private fun stubPokemonDaoGetAllAsFlow(entities: List<PokemonEntity> = emptyList()) {
        whenever(listPokemonDao.getAllAsFlow()).thenReturn(flowOf(entities))
    }

    private fun stubPokemonDaoGetByIdAsFlow(entity: PokemonEntity? = getPokemonEntity()) {
        whenever(listPokemonDao.getByIdAsFlow(any())).thenReturn(flowOf(entity))
    }

}