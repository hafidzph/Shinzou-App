package com.geminiboy.finalprojectbinar.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.geminiboy.finalprojectbinar.data.local.datastore.SetDestinationPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalModule {
    companion object{
        private val Context.counterDataStore by preferencesDataStore(
            name = "DATASTORE_PREFERENCES"
        )
    }

    @Singleton
    @Provides
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> = context.counterDataStore

    @Singleton
    @Provides
    fun provideSetDestinationPreferences(dataStore: DataStore<Preferences>): SetDestinationPreferences =
        SetDestinationPreferences(dataStore)
}