package com.pavelpotapov.guessthecelebrity.di.component

import com.pavelpotapov.guessthecelebrity.presentation.activity.game.GameActivity
import com.pavelpotapov.guessthecelebrity.di.module.AppModule
import com.pavelpotapov.guessthecelebrity.di.module.PresenterModule
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class, PresenterModule::class])
@Singleton
interface AppComponent {
    fun inject(activity: GameActivity)
}