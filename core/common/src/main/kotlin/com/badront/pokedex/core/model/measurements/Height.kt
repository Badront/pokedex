package com.badront.pokedex.core.model.measurements

class Height(
    val value: Double,
    val uom: HeightUOM
) {
    fun convertTo(newUom: HeightUOM): Height {
        return if (newUom == uom) {
            this
        } else {
            Height(
                value = uom.convertTo(value, newUom),
                uom = newUom
            )
        }
    }
}