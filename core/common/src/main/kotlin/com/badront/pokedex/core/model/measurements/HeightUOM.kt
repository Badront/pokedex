package com.badront.pokedex.core.model.measurements

enum class HeightUOM(private val multiplier: Double) {
    M(100.0),
    DM(10.0),
    SM(1.0),
    MM(0.1);

    fun convertTo(value: Double, newUOM: HeightUOM): Double {
        return (value * multiplier) / newUOM.multiplier
    }
}