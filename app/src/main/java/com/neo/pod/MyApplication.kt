package com.neo.pod

import android.app.Application

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AppInjector.init(this)
    }
}