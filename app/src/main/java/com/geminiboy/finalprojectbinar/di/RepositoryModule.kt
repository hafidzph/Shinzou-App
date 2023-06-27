package com.geminiboy.finalprojectbinar.di

import com.geminiboy.finalprojectbinar.data.repository.AuthRepository
import com.geminiboy.finalprojectbinar.data.repository.AuthRepositoryImpl
import com.geminiboy.finalprojectbinar.data.repository.FlightRepository
import com.geminiboy.finalprojectbinar.data.repository.FlightRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Binds
    abstract fun provideFlightRepository(flightRepositoryImpl: FlightRepositoryImpl): FlightRepository
}