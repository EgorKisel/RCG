package com.example.rcg.viewmodel.main

import androidx.lifecycle.ViewModel
import com.example.core_network.api.GamesRemoteDataSource
import com.example.core_network.api.RawgApi
import com.example.rcg.DI
import com.example.rcg.R
import com.example.rcg.di.ScreenScope
import com.example.rcg.di.ViewModelFactory
import com.example.rcg.di.ViewModelKey
import com.example.rcg.interactor.main.MainScreenInteractor
import com.example.rcg.interactor.main.MainScreenInteractorImpl
import com.example.rcg.persist.GamesDao
import com.example.rcg.persist.GamesPersistDataSource
import com.example.rcg.repository.GameCategoryRepository
import com.example.rcg.repository.SpecificCategoryRepositoryImpl
import com.example.rcg.repository.model.CategoryType
import com.example.rcg.util.ResourceProvider
import dagger.Binds
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Named

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

        @BindsInstance
        fun gamesDao(dao: GamesDao): Builder

        fun build(): MainScreenComponent
    }

    companion object {
        fun create() = with(DI.appComponent) {
            DaggerMainScreenComponent.builder()
                .resources(resources())
                .api(DI.networkComponent.api())
                .gamesDao(database().gamesDao())
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

    companion object {
        @Provides
        @Named("mostAnticipated")
        fun mostAnticipatedRepository(
            remoteDataSource: GamesRemoteDataSource,
            persistDataSource: GamesPersistDataSource,
            resources: ResourceProvider
        ): GameCategoryRepository = SpecificCategoryRepositoryImpl(
            remoteDataSource,
            persistDataSource,
            resources.string(R.string.most_anticipated),
            CategoryType.MostAnticipated
        )

        @Provides
        @Named("latestReleases")
        fun latestReleasesRepository(
            remoteDataSource: GamesRemoteDataSource,
            persistDataSource: GamesPersistDataSource,
            resources: ResourceProvider
        ): GameCategoryRepository = SpecificCategoryRepositoryImpl(
            remoteDataSource,
            persistDataSource,
            resources.string(R.string.latest_releases),
            CategoryType.LatestReleases
        )

        @Provides
        @Named("rated")
        fun ratedRepository(
            remoteDataSource: GamesRemoteDataSource,
            persistDataSource: GamesPersistDataSource,
            resources: ResourceProvider
        ): GameCategoryRepository = SpecificCategoryRepositoryImpl(
            remoteDataSource,
            persistDataSource,
            resources.string(R.string.most_rated_in_2024),
            CategoryType.Rated
        )
    }
}