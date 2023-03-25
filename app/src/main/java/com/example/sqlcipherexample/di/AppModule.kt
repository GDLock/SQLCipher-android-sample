package com.example.sqlcipherexample.di

import android.content.Context
import com.example.sqlcipherexample.data.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    private const val PASSWORD = "sample"

    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase(context, PASSWORD)
    }

    @Provides
    fun provideDao(database: AppDatabase) = database.sampleDao
}
