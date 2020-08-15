package com.tempo.sample.news;

import com.tempo.sample.network.model.NewsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import static com.tempo.sample.utils.Constants.BASE_URL;

public interface NewsService {
    @GET(BASE_URL + "v2/everything")
    Call<NewsResponse> getNewsList(
            @Query("q") String q,
            @Query("from") String from,
            @Query("sortBy") String sortBy,
            @Query("page") int page,
            @Query("pageSize") int pageSize,
            @Query("apiKey") String apiKey
    );
}
