package com.badront.pokedex.pokemon.details.presentation.mapper

import com.badront.pokedex.core.ext.androidx.palette.graphics.ColorPalette
import com.badront.pokedex.core.model.StringDesc
import com.badront.pokedex.pokemon.core.domain.model.PokemonDetails
import com.badront.pokedex.pokemon.details.presentation.model.PokemonDetailsUiModel
import com.badront.pokedex.pokemon.impl.R
import javax.inject.Inject

internal class PokemonDetailsUiModelMapperImpl @Inject constructor(
    private val typeUiMapper: PokemonDetailsTypeUiMapper
) : PokemonDetailsUiModelMapper {
    override fun map(model: PokemonDetails, palette: ColorPalette?): List<PokemonDetailsUiModel> {
        return buildList {
            if (model.types.isNotEmpty()) {
                add(PokemonDetailsUiModel.Types(model.types.map { typeUiMapper.map(it) }))
            }
            if (model.height != null || model.weight != null) {
                add(PokemonDetailsUiModel.Measurements(
                    height = model.height?.let {
                        StringDesc.StringR(
                            R.string.pokemon_height_uom,
                            arrayOf(it)
                        )
                    },
                    weight = model.weight?.let {
                        StringDesc.StringR(
                            R.string.pokemon_weight_uom,
                            arrayOf(it)
                        )
                    }
                ))
            }
            if (model.description != null) {
                add(PokemonDetailsUiModel.Description(model.description.toDesc()))
            }
        }
    }
}