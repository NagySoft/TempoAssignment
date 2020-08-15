package com.tempo.sample.network;

import android.app.Application;

public final class Network {
    private static Application mApplication;

    private Network() { }

    public static void init(Application application) {
        mApplication = application;
    }

    static Application getApplication() {
        return mApplication;
    }
}
