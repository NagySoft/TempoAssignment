package com.tempo.sample.news.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.tempo.sample.network.LiveDataNetworkRequest;
import com.tempo.sample.network.NetworkRepository;
import com.tempo.sample.network.model.BaseModel;
import com.tempo.sample.network.model.NewsResponse;
import com.tempo.sample.news.NewsService;

import javax.inject.Inject;

import static com.tempo.sample.utils.Constants.API_KEY;
import static com.tempo.sample.utils.Constants.PAGE_SIZE;

public class NewsRepository extends NetworkRepository {
    private final NewsService service;

    @Inject
    public NewsRepository(Application app, NewsService service) {
        super(app);
        this.service = service;
    }

    public LiveData<BaseModel<NewsResponse>> getNewsList(SearchModel searchModel) {
        LiveDataNetworkRequest<NewsResponse> networkRequest = new LiveDataNetworkRequest<>(getApp());
        return networkRequest.run(service.getNewsList(searchModel.getQuery(),
                searchModel.getFromDate(),
                searchModel.getSortBy(),
                searchModel.getPage(),
                PAGE_SIZE,
                API_KEY));
    }
}