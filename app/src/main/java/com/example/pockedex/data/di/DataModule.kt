package com.example.pockedex.data.di

import com.example.pockedex.BuildConfig
import com.example.pockedex.data.api.PokemonService
import com.example.pockedex.data.repository.PokemonRepository
import com.example.pockedex.data.repository.PokemonRepositoryImpl
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object DataModule {
    private const val BASE_URL = BuildConfig.URL_BASE
    fun load() {
        loadKoinModules(postModule() + apiModule())
    }

    private fun postModule(): Module {
        return module {
            single<PokemonRepository> { PokemonRepositoryImpl(get()) }
        }
    }

    private fun apiModule(): Module {
        return module {
            single<PokemonService> { createService(get()) }
            single { Moshi.Builder().add(KotlinJsonAdapterFactory()).build() }
        }
    }

    private inline fun <reified T> createService(
        moshi: Moshi
    ): T {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(T::class.java)
    }
}