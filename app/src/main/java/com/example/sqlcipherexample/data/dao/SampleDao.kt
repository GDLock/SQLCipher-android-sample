package com.example.sqlcipherexample.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.sqlcipherexample.data.model.SampleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SampleDao {

    @Insert
    suspend fun insert(entity: SampleEntity)

    @Query("DELETE FROM sample WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Query("SELECT * FROM sample")
    fun get(): Flow<List<SampleEntity>>
}
