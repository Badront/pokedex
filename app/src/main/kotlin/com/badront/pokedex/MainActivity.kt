package com.badront.pokedex

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import com.badront.pokedex.core.ext.kotlinx.coroutines.flow.observe
import com.badront.pokedex.databinding.ActivityMainBinding
import com.badront.pokedex.pokemon.list.presentation.PokemonListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: PokemonListViewModel by viewModels()
    private val binding: ActivityMainBinding by viewBinding()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel.viewStates.observe(this) { state ->
            state?.let(::bindState)
        }
    }

    private fun bindState(state: PokemonListViewModel.State) {
        val stringBuilder = StringBuilder()
        stringBuilder.appendLine("pokemons isLoading:${state.isLoading}")
        stringBuilder.appendLine("total pokemons:${state.items.size}")
        state.items.forEach { pokemon ->
            stringBuilder.appendLine("pokemon:${pokemon.id} ${pokemon.name}")
        }
        binding.log.text = stringBuilder.toString()
    }
}