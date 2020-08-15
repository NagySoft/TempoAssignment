package com.tempo.sample.network.model;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tempo.sample.news.data.model.Article;

import java.util.List;

@SuppressWarnings("unused")
public class NewsResponse extends BaseResponse {
    @Expose
    @SerializedName("articles")
    private List<Article> response;

    public List<Article> getResponse() {
        return response;
    }

    public void setResponse(List<Article> response) {
        this.response = response;
    }

    @NonNull
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
