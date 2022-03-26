package com.badront.pokedex.item.di

import com.badront.pokedex.item.core.data.ItemRepositoryImpl
import com.badront.pokedex.item.core.data.remote.ItemApi
import com.badront.pokedex.item.core.domain.ItemRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
internal abstract class ItemModule {
    @Binds
    abstract fun bindItemRepository(itemRepository: ItemRepositoryImpl): ItemRepository

    companion object {
        @Provides
        fun provideItemApi(retrofit: Retrofit): ItemApi {
            return retrofit.create(ItemApi::class.java)
        }
    }
}