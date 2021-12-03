package com.myapp.dictionaryapp.di

import android.app.Application

object DI {

    sealed class Config {
        object RELEASE : Config()
        object TEST : Config()
    }

    fun initialize(app: Application, configuration: Config = Config.RELEASE) {
        NetworkDI.initialize(app)
        MainWordDI.initialize(app, configuration)
    }
}