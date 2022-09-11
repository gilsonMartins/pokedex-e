package com.example.pockedex

import android.app.Application
import com.example.pockedex.data.di.DataModule
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
        }
        Module.load()
        DataModule.load()
    }

}