package com.badront.pokedex.database

import androidx.annotation.CallSuper
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import org.junit.After
import org.junit.Before
import java.io.IOException

open class DaoTest {
    protected lateinit var database: PokedexDatabase

    @CallSuper
    @Before
    open fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            PokedexDatabase::class.java
        ).allowMainThreadQueries().build()
    }

    @CallSuper
    @After
    @Throws(IOException::class)
    open fun close() {
        database.close()
    }
}