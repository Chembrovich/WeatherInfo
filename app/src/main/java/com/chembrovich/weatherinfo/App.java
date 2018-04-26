package com.chembrovich.weatherinfo;

import android.app.Application;

public class App extends Application {
    private static AppComponent component;

    public static AppComponent getComponent() {
        return component;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerAppComponent.builder().appModule(new AppModule(getApplicationContext())).build();
    }
}
