package com.example.rcg.viewmodel.details

import androidx.lifecycle.ViewModel
import com.example.core_network.api.RawgApi
import com.example.rcg.DI
import com.example.rcg.di.ScreenScope
import com.example.rcg.di.ViewModelFactory
import com.example.rcg.di.ViewModelKey
import com.example.rcg.interactor.details.GameDetailsInteractor
import com.example.rcg.interactor.details.GameDetailsInteractorImpl
import com.example.rcg.repository.details.GameDetailsRepository
import com.example.rcg.repository.details.GameDetailsRepositoryImpl
import dagger.Binds
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.multibindings.IntoMap

@Component(modules = [GameDetailsModule::class])
@ScreenScope
interface GameDetailsComponent {

    fun viewModelFactory(): ViewModelFactory

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun initialModel(model: GameDetailsScreenModel.Initial): Builder

        @BindsInstance
        fun api(api: RawgApi): Builder

        fun build(): GameDetailsComponent
    }

    companion object {
        fun create(model: GameDetailsScreenModel.Initial) = with(DI.appComponent) {
            DaggerGameDetailsComponent.builder()
                .initialModel(model)
                .api(DI.networkComponent.api())
                .build()
        }
    }
}

@Module
abstract class GameDetailsModule {

    @Binds
    @IntoMap
    @ViewModelKey(GameDetailsViewModel::class)
    abstract fun gameDetailsViewModel(viewModel: GameDetailsViewModel): ViewModel

    @Binds
    @ScreenScope
    abstract fun gameDetailsInteractorImpl(interactor: GameDetailsInteractorImpl): GameDetailsInteractor

    @Binds
    @ScreenScope
    abstract fun gameDetailsRepository(repository: GameDetailsRepositoryImpl): GameDetailsRepository
}