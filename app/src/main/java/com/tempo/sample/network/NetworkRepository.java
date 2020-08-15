package com.tempo.sample.network;

import android.app.Application;

public class NetworkRepository {
    private final Application mApp;

    public NetworkRepository(Application app) {
        mApp = app;
    }

    public Application getApp() {
        return mApp;
    }
}
