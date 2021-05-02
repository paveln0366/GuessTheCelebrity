package com.pavelpotapov.guessthecelebrity.di.module

import com.pavelpotapov.guessthecelebrity.presentation.activity.start.StartContract
import com.pavelpotapov.guessthecelebrity.presentation.activity.game.GameActivity
import com.pavelpotapov.guessthecelebrity.presentation.activity.start.StartPresenter
import dagger.Module
import dagger.Provides
import org.jetbrains.annotations.NotNull
import javax.inject.Singleton

@Module
class PresenterModule(activity: GameActivity) {

    private val activity: GameActivity = activity

    @Provides
    @Singleton
    @NotNull
    fun providePresenter(): StartContract.Presenter {
        return StartPresenter(activity)
    }
}