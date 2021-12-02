package com.myapp.dictionaryapp.di

import android.app.Application

object DI {
    fun initialize(app: Application) {
        NetworkDI.initialize()
        MainWordDI.initialize(app)
    }
}