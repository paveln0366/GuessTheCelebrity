package com.pavelpotapov.guessthecelebrity.presentation.base

import android.content.Context

interface BaseContract {
    interface View {
        fun getContext(): Context
    }

    interface Presenter<V : BaseContract.View> {
        fun getView(): V
        fun attachPresenter(view: V)
        fun detachPresenter()
    }
}