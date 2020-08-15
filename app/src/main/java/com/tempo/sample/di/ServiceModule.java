package com.tempo.sample.di;

import com.tempo.sample.news.NewsService;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
abstract class ServiceModule {
    @ContributesAndroidInjector
    abstract NewsService contributeUploadService();
}
