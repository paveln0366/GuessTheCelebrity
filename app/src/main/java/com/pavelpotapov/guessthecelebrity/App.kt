package com.pavelpotapov.guessthecelebrity

import android.app.Application

class App : Application() {
//    companion object {
//        lateinit var appComponent: AppComponent
//    }

    override fun onCreate() {
        super.onCreate()
//        initDagger()
    }

    private fun initDagger() {
//        appComponent = DaggerAppComponent.builder()
//                .appModule(AppModule(context = this@App))
//                .presenterModule(PresenterModule(activity = MainActivity.getInstance()))
//                .build()
    }
}