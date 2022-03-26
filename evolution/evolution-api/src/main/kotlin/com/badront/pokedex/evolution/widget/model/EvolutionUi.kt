package com.badront.pokedex.evolution.widget.model

import com.badront.pokedex.core.ext.kotlin.collections.areContentsTheSame
import com.badront.pokedex.evolution.core.domain.model.EvolutionDetails
import com.badront.pokedex.pokemon.core.domain.model.Pokemon

class EvolutionUi(
    val pokemon: Pokemon,
    val from: List<Link>,
    val to: List<Link>
) {
    class Link(
        val pokemon: Pokemon,
        val details: List<EvolutionDetails>
    ) {
        override fun equals(other: Any?): Boolean {
            if (other == null) return false
            if (other !is Link) return false
            return pokemon.id == other.pokemon.id &&
                details.areContentsTheSame(other.details)
        }

        override fun hashCode(): Int {
            var result = pokemon.id.hashCode()
            result = 31 * result + details.hashCode()
            return result
        }
    }

    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        if (other !is EvolutionUi) return false
        return pokemon.id == other.pokemon.id &&
            from.areContentsTheSame(other.from) &&
            to.areContentsTheSame(other.to)
    }

    override fun hashCode(): Int {
        var result = pokemon.id.hashCode()
        result = 31 * result + from.hashCode()
        result = 31 * result + to.hashCode()
        return result
    }
}