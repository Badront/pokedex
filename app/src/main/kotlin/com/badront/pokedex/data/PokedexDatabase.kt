package com.badront.pokedex.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.badront.pokedex.pokemon.core.data.local.PokemonDatabase
import com.badront.pokedex.pokemon.core.data.local.model.PokemonDetailsEntity
import com.badront.pokedex.pokemon.core.data.local.model.PokemonEntity
import com.badront.pokedex.pokemon.core.data.local.model.PokemonStatsEntity
import com.badront.pokedex.pokemon.core.data.local.model.PokemonTypeEntity

@Database(
    entities = [
        PokemonEntity::class,
        PokemonDetailsEntity::class,
        PokemonTypeEntity::class,
        PokemonStatsEntity::class
    ],
    version = PokedexDatabase.VERSION
)
abstract class PokedexDatabase : RoomDatabase(), PokemonDatabase {

    companion object {
        const val VERSION = 3
    }
}