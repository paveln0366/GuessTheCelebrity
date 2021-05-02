package com.pavelpotapov.guessthecelebrity;

import android.app.Application;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initDagger();
    }

    private void initDagger() {

    }
}
