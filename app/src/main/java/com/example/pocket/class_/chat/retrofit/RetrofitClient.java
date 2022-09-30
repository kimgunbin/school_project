package com.example.pocket.class_.chat.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static RetrofitClient instance = null;
    private static ApiService apiService;
    private static String baseUrl = "http://10.0.2.2:3000";

    private RetrofitClient() {
        Retrofit retrofit = new Retrofit.Builder().
                baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
        apiService = retrofit.create(ApiService.class);
    }

    public static RetrofitClient getInstance() {
        if (instance == null) {
            instance = new RetrofitClient();
        }
        return instance;
    }

    public static ApiService getApiService() {
        return apiService;
    }
}