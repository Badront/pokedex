package com.badront.pokedex.di

import android.content.Context
import com.badront.pokedex.core.util.okhttp.CurlLoggingInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppRemoteModule {

    @Provides
    @IntoSet
    fun bindHttpLoggingInterceptor(httpLoggingInterceptor: HttpLoggingInterceptor): Interceptor {
        return httpLoggingInterceptor
    }

    @Provides
    @IntoSet
    fun bindCurlLoggingInterceptor(curlLoggingInterceptor: CurlLoggingInterceptor): Interceptor {
        return curlLoggingInterceptor
    }

    @Provides
    @Singleton
    fun bindOkhttpCache(@ApplicationContext context: Context): Cache {
        val cacheDir = File(context.cacheDir, "okhttp_cache")
        val maxCacheSize = 10L * 1024L * 1024L
        return Cache(cacheDir, maxCacheSize)
    }

    @Provides
    @Singleton
    fun provideOkhttpClient(
        interceptors: Set<@JvmSuppressWildcards Interceptor>,
        cache: Cache
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .cache(cache)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .apply {
                interceptors.forEach {
                    addInterceptor(it)
                }
            }
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpLoggerLevel(): HttpLoggingInterceptor.Logger {
        return HttpLoggingInterceptor.Logger.DEFAULT
    }

    @Provides
    @Singleton
    fun provideCurlLoggingInterceptor(logger: HttpLoggingInterceptor.Logger): CurlLoggingInterceptor {
        return CurlLoggingInterceptor(logger)
    }

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(logger: HttpLoggingInterceptor.Logger): HttpLoggingInterceptor {
        return HttpLoggingInterceptor(logger).apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder().create()
    }

    @Provides
    @Singleton
    fun provideConverterFactory(gson: Gson): Converter.Factory {
        return GsonConverterFactory.create(gson)
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, converterFactory: Converter.Factory): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .baseUrl("https://pokeapi.co/api/v2/")
            .build()
    }
}