package com.badront.pokedex.pokemon.core.data.local.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class PokemonDetailsUOMMigration(startVersion: Int, endVersion: Int) : Migration(startVersion, endVersion) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("DROP TABLE `pokemon_details`")
        database.execSQL(
            """
           CREATE TABLE IF NOT EXISTS `pokemon_details` (
                `id` INTEGER NOT NULL, 
                `weight` REAL, 
                `weight_uom` TEXT, 
                `height` REAL, 
                `height_uom` TEXT, 
                PRIMARY KEY(`id`), 
                FOREIGN KEY(`id`) REFERENCES `pokemon`(`id`) ON UPDATE CASCADE ON DELETE CASCADE 
            )
        """
        )
        // since index also deletes, we need to recreate it
        database.execSQL(
            """CREATE INDEX IF NOT EXISTS `index_pokemon_details_id` ON `pokemon_details` (`id`)"""
        )
    }
}