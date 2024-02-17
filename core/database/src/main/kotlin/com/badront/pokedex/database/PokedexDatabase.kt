package com.badront.pokedex.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.badront.pokedex.database.pokemon.dao.ListPokemonDao
import com.badront.pokedex.database.pokemon.dao.PokemonDetailsDao
import com.badront.pokedex.database.pokemon.dao.PokemonStatsDao
import com.badront.pokedex.database.pokemon.dao.PokemonTypeDao
import com.badront.pokedex.database.pokemon.model.PokemonDetailsEntity
import com.badront.pokedex.database.pokemon.model.PokemonEntity
import com.badront.pokedex.database.pokemon.model.PokemonStatsEntity
import com.badront.pokedex.database.pokemon.model.PokemonTypeEntity

@Database(
    entities = [
        PokemonEntity::class,
        PokemonDetailsEntity::class,
        PokemonTypeEntity::class,
        PokemonStatsEntity::class
    ],
    version = PokedexDatabase.VERSION
)
abstract class PokedexDatabase : RoomDatabase() {
    abstract fun listPokemonDao(): ListPokemonDao
    abstract fun pokemonDetailsDao(): PokemonDetailsDao
    abstract fun pokemonStatsDao(): PokemonStatsDao
    abstract fun pokemonTypeDao(): PokemonTypeDao

    companion object {
        const val VERSION = 3
    }
}