package com.badront.pokedex.core.model.measurements

enum class WeightUOM(private val multiplier: Double) {
    KG(1000.0),
    HG(100.0),
    G(1.0),
    MG(0.001);

    fun convertTo(value: Double, newUOM: WeightUOM): Double {
        return (value * multiplier) / newUOM.multiplier
    }
}