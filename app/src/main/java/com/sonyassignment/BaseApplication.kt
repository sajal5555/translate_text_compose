package com.sonyassignment

import android.app.Application
import com.sonyassignment.di.getModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BaseApplication : Application() {
    companion object {
        private var instance: BaseApplication? = null

        fun getInstance(): BaseApplication? {
            return instance
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@BaseApplication)
            modules(getModules())
        }
    }
}