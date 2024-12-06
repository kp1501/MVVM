package com.khush.myapplication

import android.app.Application
import com.appizona.yehiahd.fastsave.FastSave
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        FastSave.init(applicationContext)
    }
}