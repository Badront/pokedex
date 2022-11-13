package com.badront.pokedex.database.pokemon.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class PokemonDetailsMigration(startVersion: Int, endVersion: Int) : Migration(startVersion, endVersion) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            """
                CREATE TABLE IF NOT EXISTS `pokemon_details` (
                    `id` INTEGER NOT NULL, 
                    `weight` INTEGER, 
                    `height` INTEGER, 
                    PRIMARY KEY(`id`), 
                    FOREIGN KEY(`id`) REFERENCES `pokemon`(`id`) ON UPDATE CASCADE ON DELETE CASCADE
                )
                """
        )
        database.execSQL(
            """CREATE INDEX IF NOT EXISTS `index_pokemon_details_id` ON `pokemon_details` (`id`)"""
        )
        database.execSQL(
            """
                CREATE TABLE IF NOT EXISTS `pokemon_type` (
                    `pokemon_id` INTEGER NOT NULL, 
                    `type` TEXT NOT NULL, 
                    `order` INTEGER NOT NULL, 
                    PRIMARY KEY(`pokemon_id`, `type`), 
                    FOREIGN KEY(`pokemon_id`) REFERENCES `pokemon`(`id`) ON UPDATE CASCADE ON DELETE CASCADE 
                )
                """
        )
        database.execSQL(
            "CREATE INDEX IF NOT EXISTS `index_pokemon_type_pokemon_id` ON `pokemon_type` (`pokemon_id`)"
        )
        database.execSQL(
            "CREATE INDEX IF NOT EXISTS `index_pokemon_type_type` ON `pokemon_type` (`type`)"
        )
        database.execSQL(
            """
                CREATE TABLE IF NOT EXISTS `pokemon_stats` (
                    `pokemon_id` INTEGER NOT NULL, 
                    `name` TEXT NOT NULL, 
                    `base_value` INTEGER NOT NULL, 
                    PRIMARY KEY(`pokemon_id`, `name`), 
                    FOREIGN KEY(`pokemon_id`) REFERENCES `pokemon`(`id`) ON UPDATE CASCADE ON DELETE CASCADE 
                )
                """
        )
        database.execSQL(
            "CREATE INDEX IF NOT EXISTS `index_pokemon_stats_pokemon_id` ON `pokemon_stats` (`pokemon_id`)"
        )
    }
}