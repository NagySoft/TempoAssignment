package com.tempo.sample.di;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.tempo.sample.news.NewsService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static com.tempo.sample.utils.Constants.BASE_URL;

@Module
class AppModule {

    @Singleton
    @Provides
    Retrofit provideRetrofit() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addNetworkInterceptor(new StethoInterceptor());
        httpClient.addInterceptor(logging);
        httpClient.addInterceptor(chain -> {
            return chain.proceed(chain.request()
                    .newBuilder()
                    .build());
        });

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .client(httpClient.build())
                .build();
    }

    @Singleton
    @Provides
    NewsService provideNewsService(Retrofit retrofit) {
        return retrofit.create(NewsService.class);
    }
}