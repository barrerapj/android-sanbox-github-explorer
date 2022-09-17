package com.astrear.composeplayground

import android.app.Application
import com.astrear.composeplayground.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class SandboxApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        startKoin {
            androidLogger()
            androidContext(this@SandboxApplication)
            modules(
                appModule,
                viewModelModule,
                useCaseModule,
                mapperModule,
                apiModule,
                repositoryModule
            )
        }
    }
}