package com.example.sqlcipherexample.data.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "CREATE TABLE IF NOT EXISTS sample_new (" +
                    "id INTEGER NOT NULL PRIMARY KEY, " +
                    "name TEXT NOT NULL, " +
                    "color TEXT NOT NULL" +
                    ")"
        )

        database.execSQL(
            "INSERT INTO sample_new (id, name, color) " +
                    "SELECT id, name, " +
                    "CASE name " +
                    "WHEN 'Blue' THEN 'BLUE' " +
                    "WHEN 'Red' THEN 'RED' " +
                    "WHEN 'Green' THEN 'GREEN' " +
                    "WHEN 'White' THEN 'WHITE' " +
                    "WHEN 'Magenta' THEN 'MAGENTA' " +
                    "WHEN 'Yellow' THEN 'YELLOW' " +
                    "WHEN 'Gray' THEN 'GRAY' " +
                    "ELSE 'WHITE' END color " +
                    "FROM sample"
        )

        database.execSQL("DROP TABLE sample")
        database.execSQL("ALTER TABLE sample_new RENAME TO sample")
    }
}
