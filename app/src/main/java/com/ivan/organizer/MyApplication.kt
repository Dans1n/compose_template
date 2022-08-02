package com.ivan.organizer

import android.app.Application
import com.ivan.organizer.di.*
import com.ivan.organizer.retrofit.RetrofitBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MyApplication)
            modules(
                apiModule,
                databaseModule,
                networkModule,
                repositoryModule,
                viewModelModule
            )
        }
    }
}