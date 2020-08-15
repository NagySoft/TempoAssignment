package com.tempo.sample.di;

import android.app.Application;

import com.tempo.sample.TempoApplication;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

@Singleton
@Component(modules = {
        AndroidInjectionModule.class,
        AppModule.class,
        ViewModelModule.class,
        ActivityModule.class,
        ServiceModule.class
})
public interface AppComponent {
    void inject(TempoApplication application);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}