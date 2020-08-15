package com.tempo.sample.network;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.tempo.sample.R;
import com.tempo.sample.network.model.ErrorBody;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;

import retrofit2.Response;

@SuppressWarnings("WeakerAccess")
public final class ErrorHandler {
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");

    private ErrorHandler() {
    }

    public static String getMessage(@NonNull Context context, Throwable throwable) {
        if (throwable instanceof IOException)
            return context.getString(R.string.network_err_no_internet_connection);
        else
            return throwable.getMessage();
    }

    public static String getMessage(Response response) {
        switch (response.code()) {
            case HttpURLConnection.HTTP_BAD_REQUEST:
            case HttpURLConnection.HTTP_UNAUTHORIZED:
            case HttpURLConnection.HTTP_INTERNAL_ERROR:
                return getMessageFromResponse(response);
            default:
                try {
                    return getMessageFromResponse(response);
                } catch (Exception e) {
                    return response.message();
                }
        }
    }

    private static String getMessageFromResponse(@NonNull Response response) {
        try {
            ErrorBody errorBody = new Gson().fromJson(response.errorBody().string(), ErrorBody.class);
            return errorBody.getMessage();
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
            return response.message();
        }
    }

    private static String getErrors(List<String> errors) {
        StringBuilder sb = new StringBuilder();
        for (String error : errors) {
            sb.append(error);
            sb.append(LINE_SEPARATOR);
        }
        return sb.toString();
    }
}
