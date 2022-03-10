package com.badront.pokedex.pokemon.di

import com.badront.pokedex.core.coroutines.AppDispatchers
import com.badront.pokedex.core.coroutines.JvmAppDispatchers
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppCoreModule {
    @Binds
    @Singleton
    abstract fun provideAppDispatcher(jvmAppDispatchers: JvmAppDispatchers): AppDispatchers
}