package com.badront.pokedex.pokemon

import java.util.UUID
import kotlin.math.abs

object DataFactory {
    fun randomString(): String {
        return UUID.randomUUID().toString()
    }

    fun randomBoolean(): Boolean {
        return randomUuid() % 2 == 0L
    }

    fun randomUuid(): Long {
        return System.nanoTime()
    }

    fun randomInt(): Int {
        return randomUuid().toInt()
    }

    fun randomAbsInt(): Int {
        return abs(randomInt())
    }
}