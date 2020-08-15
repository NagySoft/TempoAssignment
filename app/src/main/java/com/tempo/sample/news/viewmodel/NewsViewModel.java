package com.tempo.sample.news.viewmodel;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.tempo.sample.network.model.BaseModel;
import com.tempo.sample.network.model.NewsResponse;
import com.tempo.sample.news.data.NewsRepository;
import com.tempo.sample.news.data.SearchModel;

import javax.inject.Inject;

public class NewsViewModel extends ViewModel {
    private final MutableLiveData<SearchModel> liveData;
    private final LiveData<BaseModel<NewsResponse>> observable;

    @Inject
    public NewsViewModel(NewsRepository repository) {
        liveData = new MutableLiveData<>();
        observable = Transformations.switchMap(liveData, input -> repository.getNewsList(input));
    }

    public void getNewsList(SearchModel searchModel) {
        liveData.setValue(searchModel);
    }

    public LiveData<BaseModel<NewsResponse>> getObservable() {
        return observable;
    }
}
