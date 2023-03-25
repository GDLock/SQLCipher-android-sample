package com.example.sqlcipherexample.data.utils

import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SQLiteException
import net.sqlcipher.database.SQLiteStatement
import java.io.File
import java.io.FileNotFoundException

fun encrypt(originalFile: File, passphrase: ByteArray) {
    if (!originalFile.exists()) throw FileNotFoundException(originalFile.absolutePath + " not found")

    var database: SQLiteDatabase? = null
    val tempFile = File.createTempFile("sqlutils", "tmp")
    try {
        database = SQLiteDatabase.openDatabase(
            originalFile.absolutePath, "", null, SQLiteDatabase.OPEN_READWRITE
        )
        val version = database.version
        database.close()

        database = SQLiteDatabase.openDatabase(
            tempFile.absolutePath, passphrase, null, SQLiteDatabase.OPEN_READWRITE, null, null
        )

        val statement: SQLiteStatement = database.compileStatement("ATTACH DATABASE ? AS plaintext KEY ''")
        statement.bindString(1, originalFile.absolutePath)
        statement.execute()

        database.rawExecSQL("SELECT sqlcipher_export('main', 'plaintext')")
        database.rawExecSQL("DETACH DATABASE plaintext")
        database.version = version

        statement.close()
        database.close()
        originalFile.delete()
        tempFile.renameTo(originalFile)
    } catch (e: UnsatisfiedLinkError) {
        database?.close()
        tempFile.delete()
        throw SQLiteException("You must be call SQLiteDatabase.loadLibs(context) before invoke this method")
    }
}

fun getDatabaseState(databaseFile: File): DatabaseState {
    if (!databaseFile.exists()) return DatabaseState.DOES_NOT_EXIST

    var database: SQLiteDatabase? = null
    return try {
        database = SQLiteDatabase.openDatabase(
            databaseFile.absolutePath, "", null, SQLiteDatabase.OPEN_READONLY
        )
        database.version
        DatabaseState.UNENCRYPTED
    } catch (e: Exception) {
        DatabaseState.ENCRYPTED
    } catch (e: UnsatisfiedLinkError) {
        throw SQLiteException("You must be call SQLiteDatabase.loadLibs(context) before invoke this method")
    } finally {
        database?.close()
    }
}
