package com.example.sqlcipherexample.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.sqlcipherexample.data.model.SampleEntity


@Database(
    entities = [SampleEntity::class],
    exportSchema = false,
    version = 2
)
abstract class AppDatabaseImpl: RoomDatabase(), AppDatabase
