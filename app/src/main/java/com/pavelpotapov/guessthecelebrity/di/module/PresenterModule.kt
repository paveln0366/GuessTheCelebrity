package com.pavelpotapov.guessthecelebrity.di.module

import com.pavelpotapov.guessthecelebrity.view.activity.game.GameActivity
import com.pavelpotapov.guessthecelebrity.presenter.activity.game.GamePresenter
import com.pavelpotapov.guessthecelebrity.presenter.activity.game.IGamePresenter
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
    fun providePresenter(): IGamePresenter {
        return GamePresenter(activity)
    }
}