package com.example.core_network.di

import com.example.core_network.api.RawgApi
import dagger.Component
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Component(modules = [NetworkModule::class])
@Singleton
interface NetworkComponent {

    fun api(): RawgApi
}

@Module
abstract class NetworkModule {

    companion object {

        private const val BASE_URL = "https://api.rawg.io/"

        @Provides
        @Singleton
        fun provideApi(): RawgApi = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor())
                    .build()
            )
            .build()
            .create(RawgApi::class.java)
    }
}