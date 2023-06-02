package com.nevi.pokemonswiki.di

import com.nevi.pokemonswiki.Consts
import com.nevi.pokemonswiki.model.berries.api.BerryApi
import com.nevi.pokemonswiki.model.pokemons.api.PokemonApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkModule {

    companion object {

        @Provides
        @Singleton
        fun provideOkHttpClient(): OkHttpClient {
            return OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
                .build()
        }

        @Provides
        @Singleton
        fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
            return Retrofit.Builder()
                .baseUrl(Consts.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
        }

        @Provides
        @Singleton
        fun providePokemonApi(retrofit: Retrofit): PokemonApi {
            return retrofit.create(PokemonApi::class.java)
        }

        @Provides
        @Singleton
        fun provideBerryApi(retrofit: Retrofit): BerryApi {
            return retrofit.create(BerryApi::class.java)
        }
    }

}