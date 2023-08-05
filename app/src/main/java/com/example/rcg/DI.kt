package com.example.rcg

import com.example.core_network.di.NetworkComponent
import com.example.rcg.di.AppComponent

object DI {
    lateinit var appComponent: AppComponent
        internal set

    lateinit var networkComponent: NetworkComponent
        internal set
}