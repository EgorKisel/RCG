package com.example.rcg.di

import android.content.Context
import com.example.rcg.util.AndroidResourceProvider
import com.example.rcg.util.ResourceProvider
import dagger.Binds
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import javax.inject.Singleton

@Component(modules = [AppModule::class])
@Singleton
interface AppComponent {

    fun resources(): ResourceProvider

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun appContext(context: Context): Builder

        fun build(): AppComponent
    }
}

@Module
abstract class AppModule {
    @Binds
    @Singleton
    abstract fun bindResourceProvider(provider: AndroidResourceProvider): ResourceProvider
}