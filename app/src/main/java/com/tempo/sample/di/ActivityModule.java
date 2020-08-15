package com.tempo.sample.di;

import com.tempo.sample.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
abstract class ActivityModule {
    @ContributesAndroidInjector(modules = NewsFragmentBuildersModule.class)
    abstract MainActivity contributeMainActivity();
}