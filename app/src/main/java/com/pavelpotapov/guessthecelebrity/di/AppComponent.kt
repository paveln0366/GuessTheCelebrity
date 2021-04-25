package com.pavelpotapov.guessthecelebrity.di

import com.pavelpotapov.guessthecelebrity.GameActivity
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class, PresenterModule::class])
@Singleton
interface AppComponent {
    fun inject(activity: GameActivity)
}