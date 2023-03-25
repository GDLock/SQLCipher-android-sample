package com.example.sqlcipherexample.data

import android.content.Context
import androidx.room.Room
import com.example.sqlcipherexample.data.dao.SampleDao
import com.example.sqlcipherexample.data.migration.MIGRATION_1_2
import com.example.sqlcipherexample.data.utils.DatabaseState
import com.example.sqlcipherexample.data.utils.encrypt
import com.example.sqlcipherexample.data.utils.getDatabaseState
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory

interface AppDatabase {

    val sampleDao: SampleDao

    companion object {
        private const val DATABASE_NAME = "sample"

        operator fun invoke(context: Context, key: String): AppDatabase {
            val path = context.getDatabasePath(DATABASE_NAME)
            val passphrase: ByteArray = SQLiteDatabase.getBytes(key.toCharArray())
            SQLiteDatabase.loadLibs(context)
            if (getDatabaseState(path) == DatabaseState.UNENCRYPTED) {
                encrypt(path, passphrase)
            }
            val factory = SupportFactory(passphrase)
            return Room.databaseBuilder(context, AppDatabaseImpl::class.java, DATABASE_NAME)
                .addMigrations(MIGRATION_1_2)
                .openHelperFactory(factory)
                .build()
        }
    }
}
