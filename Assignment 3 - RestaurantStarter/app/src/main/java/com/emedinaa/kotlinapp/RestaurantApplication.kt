package com.emedinaa.kotlinapp

import android.app.Application
import com.emedinaa.kotlinapp.di.ServiceLocator

class RestaurantApplication :Application() {

    override fun onCreate() {
        super.onCreate()
        ServiceLocator.setup(this)
    }
}