package com.example.rcg

import android.app.Application
import androidx.room.Room
import com.example.core_network.BuildConfig
import com.example.core_network.di.DaggerNetworkComponent
import com.example.rcg.di.DaggerAppComponent
import com.example.rcg.persist.AppDatabase
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        val db = initDatabase()
        initDI(db)
    }

    private fun initDatabase(): AppDatabase {
        return Room.databaseBuilder(this, AppDatabase::class.java, "database").build()
    }

    private fun initDI(db: AppDatabase) {
        DI.appComponent = DaggerAppComponent.builder()
            .appContext(this)
            .database(db)
            .build()

        DI.networkComponent = DaggerNetworkComponent.builder()
            .key(DI.appComponent.resources().string(BuildConfig.api_key.toInt()))
            .build()
    }
}