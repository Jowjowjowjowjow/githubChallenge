package com.jonathan.santos.githubchallenge.di

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class KoinInitializer: Application() {
    override fun onCreate() {
        super.onCreate()
        loadModules()
    }

    private fun loadModules() {
        startKoin {
           androidContext(this@KoinInitializer)
            modules(
                listOf(
                    appModule,
                    networkModule
                )
            )
        }
    }
}