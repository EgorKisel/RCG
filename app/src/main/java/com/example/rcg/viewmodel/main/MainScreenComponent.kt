package com.example.rcg.viewmodel.main

import androidx.lifecycle.ViewModel
import com.example.core_network.api.RawgApi
import com.example.rcg.DI
import com.example.rcg.di.ScreenScope
import com.example.rcg.di.ViewModelFactory
import com.example.rcg.di.ViewModelKey
import com.example.rcg.interactor.main.MainScreenInteractor
import com.example.rcg.interactor.main.MainScreenInteractorImpl
import com.example.rcg.util.ResourceProvider
import dagger.Binds
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.multibindings.IntoMap

@Component(modules = [MainScreenModule::class])
@ScreenScope
interface MainScreenComponent {

    fun viewModelFactory(): ViewModelFactory

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun resources(resourceProvider: ResourceProvider): Builder

        @BindsInstance
        fun api(api: RawgApi): Builder

        fun build(): MainScreenComponent
    }

    companion object {
        fun create() = with(DI.appComponent) {
            DaggerMainScreenComponent.builder()
                .resources(resources())
                .api(DI.networkComponent.api())
                .build()
        }
    }
}

@Module
abstract class MainScreenModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainScreenVewModel::class)
    abstract fun mainScreenViewModel(viewModel: MainScreenVewModel): ViewModel

    @Binds
    @ScreenScope
    abstract fun mainScreenInteractor(interactorImpl: MainScreenInteractorImpl): MainScreenInteractor
}