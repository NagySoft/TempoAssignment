package com.tempo.sample.di;

import com.tempo.sample.news.view.ArticleFragment;
import com.tempo.sample.news.view.ArticlesFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class NewsFragmentBuildersModule {
    @ContributesAndroidInjector
    abstract ArticlesFragment newsFragment();

    @ContributesAndroidInjector
    abstract ArticleFragment newsDetailsFragment();
}