package com.tempo.sample.network.model;

import androidx.annotation.Nullable;

import com.tempo.sample.network.NetworkStatus;

@SuppressWarnings("unused")
public class BaseModel<T> {
    private T model;
    private String message;
    private int httpCode;
    private NetworkStatus status;

    public BaseModel(T model, NetworkStatus status) {
        this(null, model, status, -1);
    }

    public BaseModel(String message, T model, NetworkStatus status) {
        this(message, model, status, -1);
    }

    public BaseModel(String message, T model, NetworkStatus status, int httpCode) {
        this.message = message;
        this.model = model;
        this.status = status;
        this.httpCode = httpCode;
    }

    public NetworkStatus getStatus() {
        return status;
    }

    public T getModel() {
        return model;
    }

    @Nullable
    public String getMessage() {
        return message;
    }

    public int getHttpCode() {
        return httpCode;
    }
}
