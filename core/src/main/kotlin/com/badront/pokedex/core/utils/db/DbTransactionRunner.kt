package com.badront.pokedex.core.utils.db

interface DbTransactionRunner {
    suspend fun <T> withTransaction(block: suspend () -> T): T
}