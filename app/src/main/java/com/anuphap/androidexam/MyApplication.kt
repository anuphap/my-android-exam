package com.anuphap.androidexam

import android.app.Application
import com.anuphap.androidexam.di.DataModule
import com.anuphap.androidexam.di.ViewModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        setupKoin()
    }

    private fun setupKoin() {
        startKoin {
            androidContext(this@MyApplication)
            modules(
                arrayListOf(
                    ViewModule.viewModule,
                    DataModule.dataModule,
                    DataModule.remoteModule
                )
            )
        }
    }
}
