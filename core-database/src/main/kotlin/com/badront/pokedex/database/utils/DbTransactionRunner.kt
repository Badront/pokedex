package com.badront.pokedex.database.utils

interface DbTransactionRunner {
    suspend fun <T> withTransaction(block: suspend () -> T): T
}