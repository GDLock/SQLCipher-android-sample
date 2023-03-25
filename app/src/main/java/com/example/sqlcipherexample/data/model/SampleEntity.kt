package com.example.sqlcipherexample.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("sample")
data class SampleEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val name: String,

    val color: ColorState
)
