package com.badront.pokedex.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.badront.pokedex.pokemon.core.data.local.PokemonDatabase
import com.badront.pokedex.pokemon.core.data.local.model.ListPokemonEntity

@Database(
    entities = [
        ListPokemonEntity::class
    ],
    version = PokedexDatabase.VERSION
)
abstract class PokedexDatabase : RoomDatabase(), PokemonDatabase {

    companion object {
        const val VERSION = 1
    }
}