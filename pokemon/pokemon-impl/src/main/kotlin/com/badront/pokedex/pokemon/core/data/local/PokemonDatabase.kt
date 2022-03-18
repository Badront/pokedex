package com.badront.pokedex.pokemon.core.data.local

import com.badront.pokedex.pokemon.core.data.local.dao.ListPokemonDao
import com.badront.pokedex.pokemon.core.data.local.dao.PokemonDetailsDao
import com.badront.pokedex.pokemon.core.data.local.dao.PokemonStatsDao
import com.badront.pokedex.pokemon.core.data.local.dao.PokemonTypeDao

interface PokemonDatabase {
    fun listPokemonDao(): ListPokemonDao
    fun pokemonDetailsDao(): PokemonDetailsDao
    fun pokemonStatsDao(): PokemonStatsDao
    fun pokemonTypeDao(): PokemonTypeDao
}