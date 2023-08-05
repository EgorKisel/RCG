package com.example.rcg

import android.app.Application
import com.example.core_network.di.DaggerNetworkComponent
import com.example.rcg.di.DaggerAppComponent

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initDI()
    }

    private fun initDI() {
        DI.appComponent = DaggerAppComponent.builder()
            .appContext(this)
            .build()

        DI.networkComponent = DaggerNetworkComponent.create()
    }
}