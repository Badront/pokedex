package com.badront.pokedex.evolution.widget.details

import android.view.ViewGroup
import by.kirich1409.viewbindingdelegate.viewBinding
import com.badront.pokedex.core.util.recycler.BaseViewHolder
import com.badront.pokedex.evolution.core.domain.model.EvolutionParam
import com.badront.pokedex.evolution.impl.R
import com.badront.pokedex.evolution.impl.databinding.LiEvolutionDetailGenderBinding

internal class EvolutionGenderViewHolder(parent: ViewGroup) :
    BaseViewHolder<EvolutionParam.Gender>(parent, R.layout.li_evolution_detail_gender) {
    private val binding: LiEvolutionDetailGenderBinding by viewBinding()
    override fun bind(item: EvolutionParam.Gender) {
        super.bind(item)
        binding.gender.setText(
            when (item) {
                EvolutionParam.Gender.Female -> R.string.evolution_gender_female
                EvolutionParam.Gender.Male -> R.string.evolution_gender_male
            }
        )
    }
}