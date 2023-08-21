package com.example.core_network.di

import com.example.core_network.api.RawgApi
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Component(modules = [NetworkModule::class])
@Singleton
interface NetworkComponent {

    fun api(): RawgApi

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun key(@Named("key") key: String): Builder

        fun build(): NetworkComponent
    }
}

@Module
abstract class NetworkModule {

    companion object {

        private const val BASE_URL = "https://api.rawg.io/"

        @Provides
        @Singleton
        fun provideApi(@Named("key") key: String): RawgApi = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .addInterceptor { chain ->
                        val originalRequest = chain.request()
                        val request = originalRequest.newBuilder()
                            .url(
                                originalRequest.url.newBuilder()
                                    .addQueryParameter("key", key)
                                    .build()
                            )
                            .build()
                        chain.proceed(request)
                    }
                    .addInterceptor(HttpLoggingInterceptor())
                    .build()
            )
            .build()
            .create(RawgApi::class.java)
    }
}