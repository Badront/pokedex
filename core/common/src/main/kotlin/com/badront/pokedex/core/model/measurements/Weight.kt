package com.badront.pokedex.core.model.measurements

class Weight(
    val value: Double,
    val uom: WeightUOM
) {
    fun convertTo(newUom: WeightUOM): Weight {
        return if (newUom == uom) {
            this
        } else {
            Weight(
                value = uom.convertTo(value, newUom),
                uom = newUom
            )
        }
    }
}