package com.tempo.sample.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public final class ErrorBody {
    @Expose
    @SerializedName("message")
    private String message;

    @Expose
    @SerializedName("code")
    private String code;

    @Expose
    @SerializedName("status")
    private String status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
