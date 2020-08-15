package com.tempo.sample.network;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.tempo.sample.network.model.BaseModel;
import com.tempo.sample.network.model.BaseResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public final class LiveDataNetworkRequest<V extends BaseResponse> {
    private final Context mContext;
    private final MutableLiveData<BaseModel<V>> mLiveData;
    private Call<V> mCall;

    public LiveDataNetworkRequest(Application application) {
        mLiveData = new MutableLiveData<>();
        mContext = application;
    }

    public LiveData<BaseModel<V>> run(Call<V> call) {
        mCall = call;
        enqueue();
        return mLiveData;
    }

    private void enqueue() {
        if (!mCall.isExecuted()) mCall.enqueue(new ResponseCallback());
    }

    private void onSuccess(V value) {
        mLiveData.setValue(new BaseModel<>(value, NetworkStatus.SUCCESS));
    }

    private void onError(Response<V> response) {
        String message = ErrorHandler.getMessage(response);
        mLiveData.setValue(new BaseModel<>(
                message, null, NetworkStatus.HTTP_FAILURE, response.code()
        ));
    }

    private final class ResponseCallback implements Callback<V> {
        @Override
        public void onResponse(Call<V> call, Response<V> response) {
            if (response.isSuccessful()) {
                V value = response.body();
                onSuccess(value);
            } else {
                onError(response);
            }
        }

        @Override
        public void onFailure(Call<V> call, Throwable throwable) {
            if (!call.isCanceled()) {
                String message = ErrorHandler.getMessage(mContext, throwable);
                mLiveData.setValue(new BaseModel<>(
                        message, null, NetworkStatus.IO_FAILURE
                ));
            }
        }
    }
}