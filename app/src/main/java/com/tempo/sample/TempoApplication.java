package com.tempo.sample;

import android.app.Activity;
import android.app.Service;
import android.content.Context;

import androidx.multidex.MultiDexApplication;

import com.facebook.stetho.Stetho;
import com.tempo.sample.di.AppInjector;
import com.tempo.sample.network.Network;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.HasServiceInjector;

public class TempoApplication extends MultiDexApplication implements
        HasActivityInjector, HasServiceInjector {
    private static final String TAG = TempoApplication.class.getSimpleName();

    @Inject
    DispatchingAndroidInjector<Activity> mActivityInjector;

    @Inject
    DispatchingAndroidInjector<Service> mServiceInjector;

    protected static Context context;

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        AppInjector.init(this);
        Network.init(this);
        Stetho.initializeWithDefaults(this);
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return mActivityInjector;
    }

    @Override
    public AndroidInjector<Service> serviceInjector() {
        return mServiceInjector;
    }
}