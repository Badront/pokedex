package com.badront.pokedex.pokemon.details.presentation.mapper

import com.badront.pokedex.core.ext.androidx.palette.graphics.ColorPalette
import com.badront.pokedex.core.model.StringDesc
import com.badront.pokedex.core.model.measurements.HeightUOM
import com.badront.pokedex.core.model.measurements.WeightUOM
import com.badront.pokedex.evolution.core.domain.model.EvolutionChain
import com.badront.pokedex.evolution.widget.model.EvolutionUi
import com.badront.pokedex.pokemon.core.domain.model.Pokemon
import com.badront.pokedex.pokemon.core.domain.model.PokemonDetails
import com.badront.pokedex.pokemon.details.presentation.model.PokemonDetailsUiModel
import com.badront.pokedex.pokemon.impl.R
import javax.inject.Inject

internal class PokemonDetailsUiModelMapperImpl @Inject constructor(
    private val typeUiMapper: PokemonDetailsTypeUiMapper
) : PokemonDetailsUiModelMapper {
    override fun map(
        model: PokemonDetails,
        palette: ColorPalette?,
        evolutionChain: EvolutionChain?
    ): List<PokemonDetailsUiModel> {
        return buildList {
            if (model.types.isNotEmpty()) {
                add(PokemonDetailsUiModel.Types(model.types.map { typeUiMapper.map(it) }))
            }
            if (model.height != null || model.weight != null) {
                add(PokemonDetailsUiModel.Measurements(
                    height = model.height?.let {
                        StringDesc.StringR(
                            R.string.pokemon_height_in_cm,
                            arrayOf(it.convertTo(HeightUOM.SM).value)
                        )
                    },
                    weight = model.weight?.let {
                        StringDesc.StringR(
                            R.string.pokemon_weight_in_kg,
                            arrayOf(it.convertTo(WeightUOM.KG).value)
                        )
                    }
                ))
            }
            if (evolutionChain != null) {
                val currentChain = evolutionChain.findCurrent { it.id == model.id }
                currentChain?.let { pokemonChain ->
                    val fromChains = evolutionChain.findParentOf { it.id == model.id }
                    val evolution = EvolutionUi(
                        pokemonChain.pokemon,
                        to = pokemonChain.evolvedTo.map { chain ->
                            mapChainToLink(chain)
                        },
                        from = fromChains.map { chain ->
                            mapChainToLink(chain)
                        }
                    )
                    add(PokemonDetailsUiModel.Evolution(evolution))
                }
            }
        }
    }

    private fun mapChainToLink(chain: EvolutionChain): EvolutionUi.Link {
        return EvolutionUi.Link(
            chain.pokemon,
            chain.details
        )
    }

    private fun EvolutionChain.findParentOf(block: (Pokemon) -> Boolean): List<EvolutionChain> {
        return if (block(pokemon)) {
            emptyList()
        } else {
            val result = mutableListOf<EvolutionChain>()
            if (evolvedTo.any { block(it.pokemon) }) {
                result.add(this)
            }
            result.addAll(evolvedTo.map { chain -> chain.findParentOf(block) }.flatten())
            result
        }
    }

    private fun EvolutionChain.findCurrent(block: (Pokemon) -> Boolean): EvolutionChain? {
        return if (block(pokemon)) {
            this
        } else {
            var current: EvolutionChain? = null
            var index = 0
            while (current == null && index < evolvedTo.size) {
                current = evolvedTo[index].findCurrent(block)
                index++
            }
            current
        }
    }
}