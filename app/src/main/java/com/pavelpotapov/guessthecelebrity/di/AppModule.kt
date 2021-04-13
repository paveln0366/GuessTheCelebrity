package com.pavelpotapov.guessthecelebrity.di

import android.content.Context
import dagger.Module
import dagger.Provides
import org.jetbrains.annotations.NotNull
import javax.inject.Singleton

@Module
class AppModule(@NotNull context: Context) {
    private val appContext: Context = context

    @Provides
    @Singleton
    fun provideContext(): Context {
        return appContext
    }
}