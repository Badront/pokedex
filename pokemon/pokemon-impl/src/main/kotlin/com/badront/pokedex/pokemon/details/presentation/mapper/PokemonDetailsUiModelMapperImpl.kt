package com.badront.pokedex.pokemon.details.presentation.mapper

import com.badront.pokedex.core.ext.androidx.palette.graphics.ColorPalette
import com.badront.pokedex.core.model.StringDesc
import com.badront.pokedex.core.model.measurements.HeightUOM
import com.badront.pokedex.core.model.measurements.WeightUOM
import com.badront.pokedex.evolution.core.domain.model.EvolutionChain
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
            if (evolutionChain != null && evolutionChain.evolvesTo.isNotEmpty()) {
                add(PokemonDetailsUiModel.Header(StringDesc.StringR(R.string.pokemon_evolution)))
                add(PokemonDetailsUiModel.Evolution(evolutionChain))
            }
        }
    }
}