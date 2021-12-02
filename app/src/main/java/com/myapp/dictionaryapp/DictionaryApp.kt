package com.myapp.dictionaryapp

import android.app.Application
import com.myapp.dictionaryapp.di.DI

class DictionaryApp : Application() {
    override fun onCreate() {
        super.onCreate()
        DI.initialize(this)
    }
}