package com.badront.pokedex.evolution.di

import com.badront.pokedex.evolution.core.data.EvolutionChainRepositoryImpl
import com.badront.pokedex.evolution.core.data.remote.EvolutionApi
import com.badront.pokedex.evolution.core.domain.EvolutionChainRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
internal abstract class EvolutionModule {

    @Binds
    abstract fun bindEvolutionChainRepository(repositoryImpl: EvolutionChainRepositoryImpl): EvolutionChainRepository

    companion object {
        @Provides
        fun provideEvolutionApi(retrofit: Retrofit): EvolutionApi {
            return retrofit.create(EvolutionApi::class.java)
        }
    }
}