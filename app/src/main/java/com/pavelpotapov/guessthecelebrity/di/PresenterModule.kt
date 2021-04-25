package com.pavelpotapov.guessthecelebrity.di

import com.pavelpotapov.guessthecelebrity.Contract
import com.pavelpotapov.guessthecelebrity.GameActivity
import com.pavelpotapov.guessthecelebrity.Presenter
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
    fun providePresenter(): Contract.Presenter {
        return Presenter(activity)
    }
}