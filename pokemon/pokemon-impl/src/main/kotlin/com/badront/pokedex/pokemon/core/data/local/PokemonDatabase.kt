package com.badront.pokedex.pokemon.core.data.local

interface PokemonDatabase {
    fun listPokemonDao(): ListPokemonDao
}